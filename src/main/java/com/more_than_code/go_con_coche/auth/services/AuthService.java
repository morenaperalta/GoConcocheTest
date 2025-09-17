package com.more_than_code.go_con_coche.auth.services;

import com.more_than_code.go_con_coche.Role.Role;
import com.more_than_code.go_con_coche.Role.RoleRepository;
import com.more_than_code.go_con_coche.auth.dtos.RegisterRequest;
import com.more_than_code.go_con_coche.auth.dtos.RegisterResponse;
import com.more_than_code.go_con_coche.registered_user.RegisteredUser;
import com.more_than_code.go_con_coche.registered_user.RegisteredUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final RegisteredUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    public RegisterResponse register(RegisterRequest registerDto) {
        Set<Role> roles = registerDto.roleIds().stream()
                .map(roleId -> roleRepository.findById(roleId)
                        .orElseThrow(() -> new IllegalArgumentException("Role not found: " + roleId)))
                .collect(Collectors.toSet());

        if (roles.stream().anyMatch(r -> r.getRole().equals("ADMIN"))) {
            throw new IllegalArgumentException("Cannot register with ADMIN role");
        }

        RegisteredUser user = RegisteredUser.builder()
                .firstName(registerDto.firstName())
                .lastName(registerDto.lastName())
                .dateOfBirth(registerDto.dateOfBirth())
                .phoneNumber(registerDto.phoneNumber())
                .username(registerDto.username())
                .email(registerDto.email())
                .password(passwordEncoder.encode(registerDto.password()))
                .roles(roles)
                .build();

        userRepository.save(user);

        return new RegisterResponse(user.getId(), user.getUsername(), user.getRoles());
    }
}
