package com.more_than_code.go_con_coche.owner_profile.dtos;

import com.more_than_code.go_con_coche.owner_profile.OwnerProfile;
import com.more_than_code.go_con_coche.registered_user.RegisteredUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

class OwnerProfileMapperTest {

    private OwnerProfileMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new OwnerProfileMapper();
    }

    @Test
    void toResponse_shouldMapAllFieldsCorrectly() {
        RegisteredUser user = new RegisteredUser();
        user.setId(10L);

        OwnerProfile ownerProfile = OwnerProfile.builder()
                .id(1L)
                .registeredUser(user)
                .imageURL("http://cloudinary.com/img.png")
                .build();

        OwnerProfileResponse response = mapper.toResponse(ownerProfile);

        assertThat(response.id()).isEqualTo(1L);
        assertThat(response.registeredUser()).isEqualTo(10L);
        assertThat(response.imageUrl()).isEqualTo("http://cloudinary.com/img.png");
    }

    @Test
    void toEntity_shouldReturnOwnerProfileInstance() {
        OwnerProfileRequest request = new OwnerProfileRequest(null);
        OwnerProfile entity = mapper.toEntity(request);

        assertThat(entity).isNotNull();
    }
}
