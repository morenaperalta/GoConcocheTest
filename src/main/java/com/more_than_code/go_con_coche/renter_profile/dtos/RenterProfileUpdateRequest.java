package com.more_than_code.go_con_coche.renter_profile.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.more_than_code.go_con_coche.renter_profile.models.TypeLicense;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Future;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@Schema(description = "Request to create a renter profile")
public record RenterProfileUpdateRequest(
        @Schema(description = "Type of driver's license", example = "B") TypeLicense typeLicense,

        @Schema(description = "Driver's license number", example = "12345678A") String licenseNumber,

        @Schema(description = "License expiration date (format: yyyy-MM-dd)", example = "2026-12-31") @Future(message = "The license can't be expired") @JsonFormat(pattern = "yyyy-MM-dd") LocalDate expiredDate,

        @Schema(description = "Driver's license image", type = "string", format = "binary") MultipartFile image) {
}
