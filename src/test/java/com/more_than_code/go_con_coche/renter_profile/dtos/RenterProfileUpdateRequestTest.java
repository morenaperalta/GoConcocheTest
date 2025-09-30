package com.more_than_code.go_con_coche.renter_profile.dtos;

import com.more_than_code.go_con_coche.renter_profile.models.TypeLicense;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;

import java.time.LocalDate;
import java.util.Set;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@DisplayName("Tests for RenterProfileUpdateRequest")
class RenterProfileUpdateRequestTest {

    private static Validator validator;

    @BeforeAll
    static void setupValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Nested
    @DisplayName("Valid request tests")
    class ValidRequestTests {

        @Test
        @DisplayName("Should validate successfully with all fields provided")
        void validRenterUpdateProfileRequest_withImage_success() {
            MockMultipartFile image = new MockMultipartFile(
                    "image",
                    "license.png",
                    "image/png",
                    "fake-content".getBytes()
            );

            RenterProfileUpdateRequest request = new RenterProfileUpdateRequest(
                    TypeLicense.B,
                    "12345678A",
                    LocalDate.of(2026, 12, 31),
                    image
            );

            Set<ConstraintViolation<RenterProfileUpdateRequest>> violations = validator.validate(request);

            assertThat(violations).isEmpty();
        }

        @Test
        @DisplayName("Should validate successfully without image")
        void validRenterProfileUpdateRequest_withoutImage_success() {
            RenterProfileUpdateRequest request = new RenterProfileUpdateRequest(
                    TypeLicense.B,
                    "12345678A",
                    LocalDate.of(2026, 12, 31),
                    null
            );

            Set<ConstraintViolation<RenterProfileUpdateRequest>> violations = validator.validate(request);

            assertThat(violations).isEmpty();
        }

        @Test
        @DisplayName("Should validate successfully with different license types")
        void validRenterProfileUpdateRequest_differentLicenseTypes_success() {
            RenterProfileUpdateRequest requestC = new RenterProfileUpdateRequest(
                    TypeLicense.C,
                    "ABC123456",
                    LocalDate.of(2027, 6, 15),
                    null
            );

            Set<ConstraintViolation<RenterProfileUpdateRequest>> violations = validator.validate(requestC);

            assertThat(violations).isEmpty();
        }
    }

    @Nested
    @DisplayName("Invalid request tests")
    class InvalidRequestTests {

        @Test
        @DisplayName("Should fail validation when expiration date is in the past")
        void invalidRequest_pastExpirationDate_shouldFail() {
            RenterProfileUpdateRequest request = new RenterProfileUpdateRequest(
                    TypeLicense.B,
                    "12345678A",
                    LocalDate.of(2020, 1, 1),
                    null
            );

            Set<ConstraintViolation<RenterProfileUpdateRequest>> violations = validator.validate(request);

            assertThat(violations).hasSize(1);
            assertThat(violations.iterator().next().getMessage())
                    .isEqualTo("The license can't be expired");
        }

        @Test
        @DisplayName("Should fail validation when expiration date is today")
        void invalidRequest_todayExpirationDate_shouldFail() {
            RenterProfileUpdateRequest request = new RenterProfileUpdateRequest(
                    TypeLicense.B,
                    "12345678A",
                    LocalDate.now(),
                    null
            );

            Set<ConstraintViolation<RenterProfileUpdateRequest>> violations = validator.validate(request);

            assertThat(violations).hasSize(1);
            assertThat(violations.iterator().next().getMessage())
                    .isEqualTo("The license can't be expired");
        }
    }
}