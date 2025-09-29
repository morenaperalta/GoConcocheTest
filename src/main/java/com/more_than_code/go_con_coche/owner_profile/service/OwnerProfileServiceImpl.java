package com.more_than_code.go_con_coche.owner_profile.service;

import com.more_than_code.go_con_coche.cloudinary.CloudinaryService;
import com.more_than_code.go_con_coche.cloudinary.DefaultImageType;
import com.more_than_code.go_con_coche.cloudinary.UploadResult;
import com.more_than_code.go_con_coche.global.EntityAlreadyExistsException;
import com.more_than_code.go_con_coche.global.EntityNotFoundException;
import com.more_than_code.go_con_coche.owner_profile.OwnerProfile;
import com.more_than_code.go_con_coche.owner_profile.OwnerProfileRepository;
import com.more_than_code.go_con_coche.owner_profile.dtos.OwnerProfileMapper;
import com.more_than_code.go_con_coche.owner_profile.dtos.OwnerProfileRequest;
import com.more_than_code.go_con_coche.owner_profile.dtos.OwnerProfileResponse;
import com.more_than_code.go_con_coche.registered_user.RegisteredUser;
import com.more_than_code.go_con_coche.registered_user.services.UserAuthService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OwnerProfileServiceImpl implements OwnerProfileService{
    private final OwnerProfileRepository ownerProfileRepository;
    private final UserAuthService userAuthService;
    private final OwnerProfileMapper ownerProfileMapper;
    private final CloudinaryService cloudinaryService;

    @Override
    public OwnerProfileResponse createOwnerProfile (OwnerProfileRequest ownerProfileRequest){

        RegisteredUser authenticatedUser = userAuthService.getAuthenticatedUser();

        if (ownerProfileRepository.findByRegisteredUserId(authenticatedUser.getId()).isPresent()) {
            throw new EntityAlreadyExistsException("Owner profile", "registeredUserId", authenticatedUser.getId().toString());
        }
        UploadResult uploadResult = cloudinaryService.resolveImage(ownerProfileRequest.image(), DefaultImageType.PROFILE);

        OwnerProfile ownerProfile = OwnerProfile.builder()
                .registeredUser(authenticatedUser)
                .imageURL(uploadResult.url())
                .publicImageId(uploadResult.publicId())
                .build();

        OwnerProfile savedOwnerProfile = ownerProfileRepository.save(ownerProfile);
        return ownerProfileMapper.toResponse(savedOwnerProfile);
    }

    @Override
    public OwnerProfileResponse getOwnerProfile() {
        RegisteredUser authenticatedUser = userAuthService.getAuthenticatedUser();

        OwnerProfile ownerProfile = ownerProfileRepository
                .findByRegisteredUserId(authenticatedUser.getId())
                .orElseThrow(() -> new EntityNotFoundException("OwnerProfile", "registeredUserId", String.valueOf(authenticatedUser.getId())));

        return ownerProfileMapper.toResponse(ownerProfile);
    }

    public OwnerProfile getOwnerProfileEntity() {
        RegisteredUser authenticatedUser = userAuthService.getAuthenticatedUser();

        return ownerProfileRepository
                .findByRegisteredUserId(authenticatedUser.getId())
                .orElseThrow(() -> new EntityNotFoundException(
                        "OwnerProfile",
                        "registeredUserId",
                        String.valueOf(authenticatedUser.getId())
                ));
    }

}
