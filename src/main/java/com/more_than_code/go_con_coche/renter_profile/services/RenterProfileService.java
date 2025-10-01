package com.more_than_code.go_con_coche.renter_profile.services;

import com.more_than_code.go_con_coche.renter_profile.dtos.RenterProfileRequest;
import com.more_than_code.go_con_coche.renter_profile.dtos.RenterProfileUpdateRequest;
import com.more_than_code.go_con_coche.renter_profile.dtos.RenterProfileResponse;
import com.more_than_code.go_con_coche.renter_profile.models.RenterProfile;

import java.util.List;

public interface RenterProfileService {
    List<RenterProfileResponse> getAllRenterProfiles();
    RenterProfileResponse createRenterProfile(RenterProfileRequest renterProfileRequest);
    RenterProfileResponse getRenterProfileById(Long id);
    RenterProfileResponse getRenterProfileByUsername(String username);
    RenterProfileResponse getMyRenterProfile();
    RenterProfile getRenterProfileObj();
    RenterProfileResponse updateRenterProfile(RenterProfileUpdateRequest renterProfileRequest);
    void deleteRenterProfileById(Long id);
    void deleteMyRenterProfile();
}