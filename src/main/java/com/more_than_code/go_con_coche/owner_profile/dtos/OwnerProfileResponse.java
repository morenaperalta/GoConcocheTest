package com.more_than_code.go_con_coche.owner_profile.dtos;

import lombok.Builder;

@Builder
public record OwnerProfileResponse(
        Long id,
        Long registeredUser,
        String imageUrl
) {
}
