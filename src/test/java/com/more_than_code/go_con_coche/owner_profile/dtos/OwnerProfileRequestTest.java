package com.more_than_code.go_con_coche.owner_profile.dtos;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;

import java.util.Set;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

class OwnerProfileRequestTest {

    private static Validator validator;

    @BeforeAll
    static void setupValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void validOwnerProfileRequest_success() {
        MockMultipartFile image = new MockMultipartFile(
                "image",
                "test.png",
                "image/png",
                "fake-content".getBytes()
        );
        OwnerProfileRequest request = new OwnerProfileRequest(image);

        Set<ConstraintViolation<OwnerProfileRequest>> violations = validator.validate(request);

        assertThat(violations).isEmpty();
    }
}
