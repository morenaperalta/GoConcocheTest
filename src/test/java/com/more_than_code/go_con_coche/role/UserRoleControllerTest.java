package com.more_than_code.go_con_coche.role;


import com.more_than_code.go_con_coche.registered_user.RegisteredUser;
import com.more_than_code.go_con_coche.registered_user.RegisteredUserRepository;
import com.more_than_code.go_con_coche.role.controller.UserRoleController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Unit tests for UserRoleController")
class UserRoleControllerTest {

    @Mock
    private RegisteredUserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private UserRoleController userRoleController;

    @Mock
    private Authentication authentication;

    private RegisteredUser user;
    private Role ownerRole;
    private Role renterRole;

    @BeforeEach
    void setUp() {
        user = RegisteredUser.builder()
                .id(1L)
                .username("sofia")
                .roles(new HashSet<>())
                .build();

        ownerRole = new Role(2L, "OWNER", new ArrayList<>());
        renterRole = new Role(3L, "RENTER", new ArrayList<>());
    }

    @Test
    @DisplayName("Add OWNER role successfully")
    void addOwnerRole_ShouldSucceed() {
        when(authentication.getName()).thenReturn("sofia");
        when(userRepository.findByUsername("sofia")).thenReturn(Optional.of(user));
        when(roleRepository.findByRoleIgnoreCase("OWNER")).thenReturn(Optional.of(ownerRole));
        when(userRepository.save(user)).thenReturn(user);

        ResponseEntity<String> response = userRoleController.addRoleToMe("OWNER", authentication);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Role added successfully: OWNER", response.getBody());
        assertTrue(user.getRoles().contains(ownerRole));

        verify(userRepository).save(user);
    }

    @Test
    @DisplayName("Remove OWNER role successfully")
    void removeOwnerRole_ShouldSucceed() {
        user.getRoles().add(ownerRole);

        when(authentication.getName()).thenReturn("sofia");
        when(userRepository.findByUsername("sofia")).thenReturn(Optional.of(user));
        when(roleRepository.findByRoleIgnoreCase("OWNER")).thenReturn(Optional.of(ownerRole));
        when(userRepository.save(user)).thenReturn(user);

        ResponseEntity<String> response = userRoleController.removeRoleFromMe("OWNER", authentication);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Role removed successfully: OWNER", response.getBody());
        assertFalse(user.getRoles().contains(ownerRole));

        verify(userRepository).save(user);
    }

    @Test
    @DisplayName("Add invalid role returns 400")
    void addInvalidRole_ShouldReturnBadRequest() {
        lenient().when(authentication.getName()).thenReturn("sofia");
        lenient().when(userRepository.findByUsername("sofia")).thenReturn(Optional.of(user));

        ResponseEntity<String> response = userRoleController.addRoleToMe("INVALID", authentication);

        assertEquals(400, response.getStatusCodeValue());
        assertEquals("Only OWNER or RENTER can be added", response.getBody());
        verify(userRepository, never()).save(any());
    }


    @Test
    @DisplayName("Remove role not owned returns 400")
    void removeRoleNotOwned_ShouldReturnBadRequest() {
        when(authentication.getName()).thenReturn("sofia");
        when(userRepository.findByUsername("sofia")).thenReturn(Optional.of(user));
        when(roleRepository.findByRoleIgnoreCase("OWNER")).thenReturn(Optional.of(ownerRole));

        ResponseEntity<String> response = userRoleController.removeRoleFromMe("OWNER", authentication);

        assertEquals(400, response.getStatusCodeValue());
        assertEquals("User doesn't have this role", response.getBody());
        verify(userRepository, never()).save(any());
    }


}


