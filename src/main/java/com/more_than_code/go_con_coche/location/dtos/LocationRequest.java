package com.more_than_code.go_con_coche.location.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Schema(description = "Request to create a location")
public record LocationRequest(
        @NotBlank
        @Size(min = 2, max = 50)
        @Pattern(regexp = "^[A-Za-z\\s\\-]+$", message = "City can contain only Latin letters, spaces or hyphens")
        @Schema(description = "Add the city name", example = "Valencia")
        String city,

        @NotBlank
        @Size(min = 6, max = 80)
        @Pattern(regexp = "^[\\w\\s\\-.,'()!@#$%^&*+={}\\[\\]|;:\"<>?/]+$", message = "Address can contain Latin letters, numbers and special characters on one line")
        @Schema(description = "Add the address name (street, number, zip code)", example = "C/Ruzafa, 4, 46004")
        String address) {
}
