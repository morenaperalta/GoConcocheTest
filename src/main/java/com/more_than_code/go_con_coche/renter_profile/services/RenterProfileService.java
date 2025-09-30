package com.more_than_code.go_con_coche.renter_profile.services;

import com.more_than_code.go_con_coche.renter_profile.dtos.RenterProfileRequest;
import com.more_than_code.go_con_coche.renter_profile.dtos.RenterProfileResponse;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface RenterProfileService {
    RenterProfileResponse createRenterProfile(RenterProfileRequest renterProfileRequest);
    RenterProfileResponse getRenterProfileByUsername(String username);
    RenterProfileResponse getOwnRenterProfile();
    List<RenterProfileResponse> getAllRenterProfiles();
}
