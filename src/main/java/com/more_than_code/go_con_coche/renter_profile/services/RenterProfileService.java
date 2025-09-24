package com.more_than_code.go_con_coche.renter_profile.services;

import com.more_than_code.go_con_coche.renter_profile.dtos.RenterProfileRequest;
import com.more_than_code.go_con_coche.renter_profile.dtos.RenterProfileResponse;

public interface RenterProfileService {
    RenterProfileResponse createRenterProfile(RenterProfileRequest renterProfileRequest);
}
