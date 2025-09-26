package com.more_than_code.go_con_coche.registered_user.services;

import com.more_than_code.go_con_coche.auth.SecurityUser;
import com.more_than_code.go_con_coche.global.EntityNotFoundException;
import com.more_than_code.go_con_coche.registered_user.RegisteredUser;
import com.more_than_code.go_con_coche.registered_user.RegisteredUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserAuthService implements UserDetailsService {
    private static final String USERNAME_FIELD = "username";
    private final RegisteredUserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws EntityNotFoundException {
        return userRepository.findByUsername(username).map(SecurityUser::new).orElseThrow(() -> new EntityNotFoundException(RegisteredUser.class.getSimpleName(), USERNAME_FIELD, username));
    }

    public RegisteredUser getAuthenticatedUser() {
        SecurityUser userDetails = (SecurityUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        String username = userDetails.getUsername();

        return userRepository.findByUsername(username).orElseThrow(() -> new EntityNotFoundException(RegisteredUser.class.getSimpleName(), USERNAME_FIELD, username));
    }

    public String extractRole(Authentication auth) {
        return auth.getAuthorities().stream().map(GrantedAuthority::getAuthority).findFirst().orElse("NO_ROLE");
    }

    public List<String> extractAllRoles(Authentication auth) {
        return auth.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
    }

    public boolean hasRole(String role) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth.getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority().equals("ROLE_" + role));
    }
}