package com.more_than_code.go_con_coche.auth;

import com.more_than_code.go_con_coche.auth.dtos.AuthRequest;
import com.more_than_code.go_con_coche.auth.dtos.AuthResponse;
import com.more_than_code.go_con_coche.auth.dtos.RegisterRequest;
import com.more_than_code.go_con_coche.auth.dtos.RegisterResponse;
import com.more_than_code.go_con_coche.auth.services.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "Auth", description = "Operations related to authentication and authorization.")
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    @Operation(summary = "Register new user", description = "Creates a new user account and returns the registered user details")
    public ResponseEntity<RegisterResponse> register(@RequestBody RegisterRequest regicterDto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(authService.register(regicterDto));
    }

    @PostMapping("/login")
    @Operation(summary = "Login users", description = "Login into a user account and returns login details")
    public ResponseEntity<AuthResponse> authenticate(@RequestBody AuthRequest loginDto,
                                                     HttpServletResponse response) {
        AuthResponse userResponse = authService.authenticate(loginDto, response);
        return ResponseEntity.ok()
                .header("Authorization", "Bearer " + userResponse.token())
                .body(userResponse);
    }

    @PostMapping("/refresh")
    @Operation(summary = "Refresh token", description = "Refresh token for users login into their accounts")
    public ResponseEntity<Void> refreshToken(HttpServletRequest request, HttpServletResponse response) {
        String accessToken = authService.refreshToken(request, response);
        return ResponseEntity.ok()
                .header("Authorization", "Bearer " + accessToken)
                .build();
    }
}
