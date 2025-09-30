package com.more_than_code.go_con_coche.renter_profile.services;

import com.more_than_code.go_con_coche.cloudinary.CloudinaryService;
import com.more_than_code.go_con_coche.cloudinary.DefaultImageType;
import com.more_than_code.go_con_coche.cloudinary.UploadResult;
import com.more_than_code.go_con_coche.global.EntityAlreadyExistsException;
import com.more_than_code.go_con_coche.global.EntityNotFoundException;
import com.more_than_code.go_con_coche.registered_user.RegisteredUser;
import com.more_than_code.go_con_coche.registered_user.services.UserAuthService;
import com.more_than_code.go_con_coche.renter_profile.dtos.RenterProfileMapper;
import com.more_than_code.go_con_coche.renter_profile.RenterProfileRepository;
import com.more_than_code.go_con_coche.renter_profile.dtos.RenterProfileRequest;
import com.more_than_code.go_con_coche.renter_profile.dtos.RenterProfileUpdateRequest;
import com.more_than_code.go_con_coche.renter_profile.dtos.RenterProfileResponse;

import com.more_than_code.go_con_coche.renter_profile.models.RenterProfile;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RenterProfileServiceImpl implements RenterProfileService{
    private final RenterProfileRepository renterProfileRepository;
    private final UserAuthService userAuthService;
    private final RenterProfileMapper renterProfileMapper;
    private final CloudinaryService cloudinaryService;

    @Override
    @Transactional
    public RenterProfileResponse createRenterProfile(RenterProfileRequest renterProfileRequest) {

        RegisteredUser authenticatedUser = userAuthService.getAuthenticatedUser();

        if (renterProfileRepository.existsByRegisteredUser(authenticatedUser)){
            throw new EntityAlreadyExistsException(RenterProfile.class.getSimpleName(), "user", authenticatedUser.getUsername());
        }

        UploadResult uploadResult = cloudinaryService.resolveImage(renterProfileRequest.image(), DefaultImageType.PROFILE);

        RenterProfile renterProfile = RenterProfile.builder()
                .registeredUser(authenticatedUser)
                .typeLicense(renterProfileRequest.typeLicense())
                .licenseNumber(renterProfileRequest.licenseNumber())
                .expiredDate(renterProfileRequest.expiredDate())
                .imageURL(uploadResult.url())
                .publicImageId(uploadResult.publicId())
                .build();

        RenterProfile savedRenterProfile = renterProfileRepository.save(renterProfile);

        return renterProfileMapper.toResponse(savedRenterProfile);
    }

    @Override
    @Transactional(readOnly = true)
    public List<RenterProfileResponse> getAllRenterProfiles() {
        List<RenterProfile> renterProfiles = renterProfileRepository.findAll();
        return renterProfiles.stream().map(renterProfileMapper::toResponse).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public RenterProfileResponse getOwnRenterProfile(){
        RegisteredUser authenticatedUser = userAuthService.getAuthenticatedUser();

        RenterProfile renterProfile = renterProfileRepository
                .findByRegisteredUserId(authenticatedUser.getId())
                .orElseThrow(() -> new EntityNotFoundException(RenterProfile.class.getSimpleName(), "id", String.valueOf(authenticatedUser.getId())));

        return renterProfileMapper.toResponse(renterProfile);
    }

    @Override
    @Transactional(readOnly = true)
    public RenterProfileResponse getRenterProfileByUsername(String username){

        RenterProfile renterProfile = renterProfileRepository.findByRegisteredUserUsername(username).orElseThrow(() -> new EntityNotFoundException(RenterProfile.class.getSimpleName(), "username", username));

        return renterProfileMapper.toResponse(renterProfile);
    }

    @Override
    @Transactional(readOnly = true)
    public RenterProfile getRenterProfileObj() {
        RegisteredUser authenticatedUser = userAuthService.getAuthenticatedUser();

        return renterProfileRepository.findByRegisteredUserId(authenticatedUser.getId())
                .orElseThrow(() -> new EntityNotFoundException(RenterProfile.class.getSimpleName(), "id", String.valueOf(authenticatedUser.getId())));
    }

    @Override
    @Transactional
    public RenterProfileResponse updateRenterProfile(RenterProfileUpdateRequest renterProfileRequest) {

        RegisteredUser authenticatedUser = userAuthService.getAuthenticatedUser();

        RenterProfile existingProfile = renterProfileRepository
                .findByRegisteredUserId(authenticatedUser.getId())
                .orElseThrow(() -> new EntityNotFoundException(
                        RenterProfile.class.getSimpleName(),
                        "user",
                        authenticatedUser.getUsername()
                ));

        if (renterProfileRequest.typeLicense() != null) {
            existingProfile.setTypeLicense(renterProfileRequest.typeLicense());
        }

        if (renterProfileRequest.licenseNumber() != null && !renterProfileRequest.licenseNumber().isBlank()) {
            existingProfile.setLicenseNumber(renterProfileRequest.licenseNumber());
        }

        if (renterProfileRequest.expiredDate() != null) {
            existingProfile.setExpiredDate(renterProfileRequest.expiredDate());
        }

        if (renterProfileRequest.image() != null) {
            if (existingProfile.getPublicImageId() != null) {
                cloudinaryService.delete(existingProfile.getPublicImageId());
            }

            UploadResult uploadResult = cloudinaryService.resolveImage(
                    renterProfileRequest.image(),
                    DefaultImageType.PROFILE
            );
            existingProfile.setImageURL(uploadResult.url());
            existingProfile.setPublicImageId(uploadResult.publicId());
        }

        RenterProfile updatedProfile = renterProfileRepository.save(existingProfile);

        return renterProfileMapper.toResponse(updatedProfile);
    }
}