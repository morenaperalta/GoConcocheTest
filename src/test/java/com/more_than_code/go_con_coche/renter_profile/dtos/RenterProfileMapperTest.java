package com.more_than_code.go_con_coche.renter_profile.dtos;

import com.more_than_code.go_con_coche.registered_user.RegisteredUser;
import com.more_than_code.go_con_coche.renter_profile.models.RenterProfile;
import com.more_than_code.go_con_coche.renter_profile.models.TypeLicense;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@DisplayName("Tests for RenterProfileMapper")
class RenterProfileMapperTest {

    private RenterProfileMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new RenterProfileMapper();
    }

    @Test
    @DisplayName("Should map all fields correctly to response")
    void toResponse_shouldMapAllFieldsCorrectly() {
        RegisteredUser user = new RegisteredUser();
        user.setId(10L);

        RenterProfile renterProfile = RenterProfile.builder()
                .id(1L)
                .registeredUser(user)
                .typeLicense(TypeLicense.B)
                .licenseNumber("12345678A")
                .expiredDate(LocalDate.of(2026, 12, 31))
                .imageURL("http://cloudinary.com/img.png")
                .build();

        RenterProfileResponse response = mapper.toResponse(renterProfile);

        assertThat(response.id()).isEqualTo(1L);
        assertThat(response.registeredUser()).isEqualTo(10L);
        assertThat(response.typeLicense()).isEqualTo("B");
        assertThat(response.licenseNumber()).isEqualTo("12345678A");
        assertThat(response.expiredDate()).isEqualTo(LocalDate.of(2026, 12, 31));
        assertThat(response.imageUrl()).isEqualTo("http://cloudinary.com/img.png");
    }

    @Test
    @DisplayName("Should map different license types correctly")
    void toResponse_shouldMapDifferentLicenseTypes() {
        RegisteredUser user = new RegisteredUser();
        user.setId(5L);

        RenterProfile renterProfile = RenterProfile.builder()
                .id(2L)
                .registeredUser(user)
                .typeLicense(TypeLicense.C)
                .licenseNumber("ABC123456")
                .expiredDate(LocalDate.of(2027, 6, 15))
                .imageURL("http://cloudinary.com/license.jpg")
                .build();

        RenterProfileResponse response = mapper.toResponse(renterProfile);

        assertThat(response.typeLicense()).isEqualTo("C");
    }

    @Test
    @DisplayName("Should return RenterProfile instance from request")
    void toEntity_shouldReturnRenterProfileInstance() {
        RenterProfileRequest request = new RenterProfileRequest(
                TypeLicense.B,
                "12345678A",
                LocalDate.of(2026, 12, 31),
                null
        );

        RenterProfile entity = mapper.toEntity(request);

        assertThat(entity).isNotNull();
    }
}