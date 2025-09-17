package com.more_than_code.go_con_coche.auth.dtos;

import com.more_than_code.go_con_coche.Role.Role;
import jakarta.validation.constraints.*;

import java.time.LocalDate;
import java.util.Set;

public record RegisterRequest(@NotBlank
                              @Size(min = 2, max = 50)
                              @Pattern(regexp = "^[A-Za-z'-]+$", message = "First name must contain only Latin letters, hyphen or apostrophe")
                              String firstName,
                              @NotBlank
                              @Size(min = 2, max = 50)
                              @Pattern(regexp = "^[A-Za-z'-]+$", message = "Second name must contain only Latin letters, hyphen or apostrophe")
                              String secondName,
                              @NotNull
                              @Past(message = "Date of birth must be in the past")
                              LocalDate dateOfBirth,
                              @NotNull
                              @Pattern(regexp = "^\\+?[0-9]{10,15}$", message = "Phone number must be valid and contain 10–15 digits (optionally starting with +)")
                              String phoneNumber,
                              @NotBlank(message = "Username cannot be blank") @Size(max = 30, message = "Username cannot be longer than 30 characters!")
                              String username,
                              @NotBlank(message = "Email cannot be blank") @Email(message = "Email should be valid!")
                              @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", message = "Email format is invalid")
                              String email,
                              @NotBlank(message = "Password cannot be blank") @Size(min = 8, message = "Password should be at least 8 characters long!")
                              @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[^a-zA-Z0-9]).{8,}$", message = "Password must be at least 8 characters and include uppercase, lowercase, number and special character")
                              String password,
                              @NotEmpty(message = "Roles cannot be empty")
                              @NotNull Set<Role> roles) {
}