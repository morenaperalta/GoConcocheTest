package com.more_than_code.go_con_coche.owner_profile.service;

import com.more_than_code.go_con_coche.owner_profile.OwnerProfile;
import com.more_than_code.go_con_coche.owner_profile.dtos.OwnerProfileRequest;
import com.more_than_code.go_con_coche.owner_profile.dtos.OwnerProfileResponse;

import java.util.List;

public interface OwnerProfileService {
    OwnerProfileResponse createOwnerProfile(OwnerProfileRequest ownerProfileRequest);
    OwnerProfileResponse getOwnerProfile();

    OwnerProfile getOwnerProfileObj();

    List<OwnerProfileResponse> getAllOwnerProfiles();
    OwnerProfileResponse getOwnerProfileById(Long id);
    OwnerProfileResponse updateMyOwnerProfile (OwnerProfileRequest request);
    void deleteMyOwnerProfile();
    void deleteOwnerProfileById(Long id);
}
