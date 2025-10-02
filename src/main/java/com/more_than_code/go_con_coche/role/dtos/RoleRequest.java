package com.more_than_code.go_con_coche.role.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
@Schema(description = "Request to create a role")
public record RoleRequest (
    @NotBlank(message = "Role name is required")
    @Size(min = 5, max = 20, message = "Role name must be between 5 and 20 characters")
    @Pattern(
            regexp = "^ROLE_[A-Z_]+$",
            message = "Role name must start with 'ROLE_' and contain only uppercase letters and underscores"
    ) @Schema(description = "You should add role name", example = "renter")
    String roleName
) {
}