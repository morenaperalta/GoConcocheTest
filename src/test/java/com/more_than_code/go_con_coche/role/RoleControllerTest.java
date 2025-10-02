package com.more_than_code.go_con_coche.role;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.more_than_code.go_con_coche.registered_user.RegisteredUserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@DisplayName("Integration tests for RoleController using JWT")
@Sql(scripts = "/data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Transactional
public class RoleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private RegisteredUserRepository registeredUserRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Test
    @DisplayName("GET /roles should return all roles and a 200 status")
    @WithMockUser(roles = "ADMIN")
    void getAllRoles_AsAdmin_shouldReturnAllRoles() throws Exception {
        mockMvc.perform(get("/api/roles"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(3))
                .andExpect(jsonPath("$[?(@.roleName == 'ROLE_ADMIN')]").exists())
                .andExpect(jsonPath("$[?(@.roleName == 'ROLE_RENTER')]").exists())
                .andExpect(jsonPath("$[?(@.roleName == 'ROLE_OWNER')]").exists());
    }

    @Test
    @DisplayName("GET /api/roles returns 403 for non-ADMIN user")
    @WithMockUser(roles = "RENTER")
    void getAllRoles_AsRenter_ShouldReturnForbidden() throws Exception {
        mockMvc.perform(get("/api/roles")).andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("GET /api/roles returns 403 for unauthenticated user")
    void getAllRoles_Unauthenticated_ShouldReturnForbidden() throws Exception {
        mockMvc.perform(get("/api/roles")).andExpect(status().isForbidden());
    }

}
