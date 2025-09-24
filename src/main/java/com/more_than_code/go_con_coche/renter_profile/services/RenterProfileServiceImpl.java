package com.more_than_code.go_con_coche.renter_profile.services;

import com.more_than_code.go_con_coche.global.EntityAlreadyExistsException;
import com.more_than_code.go_con_coche.registered_user.RegisteredUser;
import com.more_than_code.go_con_coche.registered_user.services.UserAuthService;
import com.more_than_code.go_con_coche.renter_profile.dtos.RenterProfileMapper;
import com.more_than_code.go_con_coche.renter_profile.RenterProfileRepository;
import com.more_than_code.go_con_coche.renter_profile.dtos.RenterProfileRequest;
import com.more_than_code.go_con_coche.renter_profile.dtos.RenterProfileResponse;

import com.more_than_code.go_con_coche.renter_profile.models.RenterProfile;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RenterProfileServiceImpl implements RenterProfileService{
    private final RenterProfileRepository renterProfileRepository;
    private final UserAuthService userAuthService;
    private final RenterProfileMapper renterProfileMapper;

    @Override
    @Transactional
    public RenterProfileResponse createRenterProfile(RenterProfileRequest renterProfileRequest) {

        RenterProfile renterProfile = renterProfileMapper.toEntity(renterProfileRequest);
        RegisteredUser user = userAuthService.getAuthenticatedUser();
        renterProfile.setRegisteredUser(user);

        if (renterProfileRepository.existsByRegisteredUser(user)){
         throw new EntityAlreadyExistsException(RenterProfile.class.getSimpleName(), "user", user.getUsername());
        }

        RenterProfile savedRenterProfile = renterProfileRepository.save(renterProfile);

        return renterProfileMapper.toResponse(savedRenterProfile);
    }
}
