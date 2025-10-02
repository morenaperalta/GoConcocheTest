package com.more_than_code.go_con_coche.location.dtos;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

class LocationRequestTest {
    private static Validator validator;

    @BeforeAll
    static void setupValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void validLocationRequest_success() {
        LocationRequest request = new LocationRequest(
                "Madrid",
                "Centro",
                "Pl Tirso de Molina, 9"
        );

        Set<ConstraintViolation<LocationRequest>> violations = validator.validate(request);

        assertThat(violations).isEmpty();
    }

    @Test
    void blankCity_NoValidation() {
        LocationRequest request = new LocationRequest(
                " ",
                "Centro",
                "Pl Tirso de Molina, 9"
        );

        Set<ConstraintViolation<LocationRequest>> violations = validator.validate(request);

        assertThat(violations).anyMatch(v -> v.getPropertyPath().toString().equals("city"));
    }

    @Test
    void blankDistrict_NoValidation() {
        LocationRequest request = new LocationRequest(
                "Madrid",
                " ",
                "Pl Tirso de Molina, 9"
        );

        Set<ConstraintViolation<LocationRequest>> violations = validator.validate(request);

        assertThat(violations).anyMatch(v -> v.getPropertyPath().toString().equals("district"));
    }

    @Test
    void tooShortAddress_NoValidation() {
        LocationRequest request = new LocationRequest(
                "Madrid",
                "Centro",
                "C"
        );

        Set<ConstraintViolation<LocationRequest>> violations = validator.validate(request);

        assertThat(violations).anyMatch(v -> v.getPropertyPath().toString().equals("address"));
    }

    @Test
    void address_shouldBeValid() {
        LocationRequest request = new LocationRequest(
                "San Sebastian",
                "Centro",
                "Federico Garcia Lorca Pasealekua, 1, 20012"
        );

        Set<ConstraintViolation<LocationRequest>> violations = validator.validate(request);

        assertThat(violations).isEmpty();
    }
}
