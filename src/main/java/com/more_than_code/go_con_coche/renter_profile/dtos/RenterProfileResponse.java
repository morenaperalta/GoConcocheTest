package com.more_than_code.go_con_coche.renter_profile.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Builder
public record RenterProfileResponse(
        Long id,
        Long registeredUser,
        String typeLicense,
        String licenseNumber,
        @JsonFormat(pattern = "dd-MM-yyyy")
        LocalDate expiredDate,
        String imageUrl
) {
}
