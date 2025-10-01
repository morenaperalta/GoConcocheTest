package com.more_than_code.go_con_coche.owner_profile;

import com.more_than_code.go_con_coche.owner_profile.dtos.OwnerProfileRequest;
import com.more_than_code.go_con_coche.owner_profile.dtos.OwnerProfileResponse;
import com.more_than_code.go_con_coche.owner_profile.service.OwnerProfileService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/owner-profiles")
@RequiredArgsConstructor
public class OwnerProfileController {
    private final OwnerProfileService ownerProfileService;

    @PostMapping("")
    public ResponseEntity<OwnerProfileResponse> createOwnerProfile(@Valid @ModelAttribute OwnerProfileRequest ownerProfileRequest){
        OwnerProfileResponse createdOwnerProfile = ownerProfileService.createOwnerProfile(ownerProfileRequest);
        return new ResponseEntity<>(createdOwnerProfile, HttpStatus.CREATED);
    }

    @GetMapping("/profile")
    public ResponseEntity<OwnerProfileResponse> getOwnerProfile() {
        OwnerProfileResponse ownerProfileResponse = ownerProfileService.getOwnerProfile();
        return ResponseEntity.ok(ownerProfileResponse);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<OwnerProfileResponse>> getAllOwnerProfiles() {
        return ResponseEntity.ok(ownerProfileService.getAllOwnerProfiles());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<OwnerProfileResponse> getOwnerProfileById(@PathVariable Long id) {
        return ResponseEntity.ok(ownerProfileService.getOwnerProfileById(id));
    }

    @PutMapping("/profile")
    public ResponseEntity<OwnerProfileResponse> updateOwnerProfile(@Valid @ModelAttribute OwnerProfileRequest request) {
        return ResponseEntity.ok(ownerProfileService.updateOwnerProfile(request));
    }
}
