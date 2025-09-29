package com.more_than_code.go_con_coche.owner_profile;

import com.more_than_code.go_con_coche.owner_profile.dtos.OwnerProfileRequest;
import com.more_than_code.go_con_coche.owner_profile.dtos.OwnerProfileResponse;
import com.more_than_code.go_con_coche.owner_profile.service.OwnerProfileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/owner-profiles")
@RequiredArgsConstructor
@Tag(name = "Owner profile", description = "Management of owner profiles")
public class OwnerProfileController {
    private final OwnerProfileService ownerProfileService;

    @PostMapping("")
    @Operation(summary = "Create new owner profile", description = "Creation of a new owner profile")
    public ResponseEntity<OwnerProfileResponse> createOwnerProfile(@Valid @ModelAttribute OwnerProfileRequest ownerProfileRequest){
        OwnerProfileResponse createdOwnerProfile = ownerProfileService.createOwnerProfile(ownerProfileRequest);
        return new ResponseEntity<>(createdOwnerProfile, HttpStatus.CREATED);
    }

    @GetMapping("/profile")
    @Operation(summary = "Get own owner profile", description = "Get own profile for owners")
    public ResponseEntity<OwnerProfileResponse> getOwnerProfile() {
        OwnerProfileResponse ownerProfileResponse = ownerProfileService.getOwnerProfile();
        return ResponseEntity.ok(ownerProfileResponse);
    }
}
