package com.more_than_code.go_con_coche.owner_profile.dtos;


import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.web.multipart.MultipartFile;

@Schema(description = "Request to create a owner profile")
public record OwnerProfileRequest (
        @Schema(description = "Add your profile image")
        MultipartFile image
){
}
