package com.more_than_code.go_con_coche.renter_profile;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser ;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Sql(scripts = "/data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@DisplayName("RenterProfileControllerTest")
public class RenterProfileControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private MockMultipartFile validImage;
    private MockMultipartFile invalidImage;

    @BeforeEach
    void setUp() {
        validImage = new MockMultipartFile("image", "license.png", "image/png", "fake-image-content".getBytes());
        invalidImage = new MockMultipartFile("image", "license.txt", "text/plain", "not-an-image".getBytes());
    }

    @Nested
    @DisplayName("Create Renter Profile")
    class CreateRenterProfileTests {

        @Test
        @DisplayName("Should return 400 when license type is null")
        @WithMockUser (username = "renter1", roles = {"RENTER"})
        void createRenterProfile_WithNullLicenseType_ShouldReturnBadRequest() throws Exception {
            mockMvc.perform(multipart("/api/renter-profiles")
                            .file(validImage)
                            .param("licenseNumber", "12345678A")
                            .param("expiredDate", "2026-12-31")
                            .contentType(MediaType.MULTIPART_FORM_DATA))
                    .andExpect(status().isBadRequest());
        }

        @Test
        @DisplayName("Should return 400 when license number is blank")
        @WithMockUser (username = "renter1", roles = {"RENTER"})
        void createRenterProfile_WithBlankLicenseNumber_ShouldReturnBadRequest() throws Exception {
            mockMvc.perform(multipart("/api/renter-profiles")
                            .file(validImage)
                            .param("typeLicense", "B")
                            .param("licenseNumber", "")
                            .param("expiredDate", "2026-12-31")
                            .contentType(MediaType.MULTIPART_FORM_DATA))
                    .andExpect(status().isBadRequest());
        }

        @Test
        @DisplayName("Should return 400 when expiration date is in the past")
        @WithMockUser (username = "renter1", roles = {"RENTER"})
        void createRenterProfile_WithPastExpirationDate_ShouldReturnBadRequest() throws Exception {
            mockMvc.perform(multipart("/api/renter-profiles")
                            .file(validImage)
                            .param("typeLicense", "B")
                            .param("licenseNumber", "12345678A")
                            .param("expiredDate", "2020-01-01")
                            .contentType(MediaType.MULTIPART_FORM_DATA))
                    .andExpect(status().isBadRequest());
        }

        @Test
        @DisplayName("Should return 403 when user does not have RENTER role")
        @WithMockUser (username = "admin1", roles = {"ADMIN"})
        void createRenterProfile_WithoutRenterRole_ShouldReturnForbidden() throws Exception {
            mockMvc.perform(multipart("/api/renter-profiles")
                            .file(validImage)
                            .param("typeLicense", "B")
                            .param("licenseNumber", "12345678A")
                            .param("expiredDate", "2026-12-31")
                            .contentType(MediaType.MULTIPART_FORM_DATA))
                    .andExpect(status().isForbidden());
        }

        @Test
        @DisplayName("Should return 403 when user is not authenticated")
        void createRenterProfile_WithoutAuthentication_ShouldReturnForbidden() throws Exception {
            mockMvc.perform(multipart("/api/renter-profiles")
                            .file(validImage)
                            .param("typeLicense", "B")
                            .param("licenseNumber", "12345678A")
                            .param("expiredDate", "2026-12-31")
                            .contentType(MediaType.MULTIPART_FORM_DATA))
                    .andExpect(status().isForbidden());
        }
    }

    @Nested
    @DisplayName("Get All Renter Profiles")
    class GetAllRenterProfilesTests {

        @Test
        @DisplayName("Should return all renter profiles when user has ADMIN role")
        @WithMockUser (username = "admin1", roles = {"ADMIN"})
        void getAllRenterProfiles_WithAdminRole_ShouldReturnList() throws Exception {
            mockMvc.perform(get("/api/renter-profiles")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$").isArray());
        }

        @Test
        @DisplayName("Should return 403 when user does not have ADMIN role")
        @WithMockUser (username = "renter1", roles = {"RENTER"})
        void getAllRenterProfiles_WithoutAdminRole_ShouldReturnForbidden() throws Exception {
            mockMvc.perform(get("/api/renter-profiles")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isForbidden());
        }

        @Test
        @DisplayName("Should return 403 when user is not authenticated")
        void getAllRenterProfiles_WithoutAuthentication_ShouldReturnForbidden() throws Exception {
            mockMvc.perform(get("/api/renter-profiles")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isForbidden());
        }
    }

    @Nested
    @DisplayName("Get My Renter Profile")
    class GetMyRenterProfileTests {

        @Test
        @DisplayName("Should return 403 when user does not have RENTER role")
        @WithMockUser (username = "admin1", roles = {"ADMIN"})
        void getMyRenterProfile_WithoutRenterRole_ShouldReturnForbidden() throws Exception {
            mockMvc.perform(get("/api/renter-profiles/me")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isForbidden());
        }

        @Test
        @DisplayName("Should return 403 when user is not authenticated")
        void getMyRenterProfile_WithoutAuthentication_ShouldReturnForbidden() throws Exception {
            mockMvc.perform(get("/api/renter-profiles/me")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isForbidden());
        }
    }

    @Nested
    @DisplayName("Get Renter Profile By Username")
    class GetRenterProfileByUsernameTests {

        @Test
        @DisplayName("Should return 403 when user does not have OWNER or RENTER role")
        @WithMockUser (username = "user1", roles = {"USER"})
        void getRenterProfileByUsername_WithoutAuthorizedRole_ShouldReturnForbidden() throws Exception {
            mockMvc.perform(get("/api/renter-profiles/renter1")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isForbidden());
        }

        @Test
        @DisplayName("Should return 403 when user is not authenticated")
        void getRenterProfileByUsername_WithoutAuthentication_ShouldReturnForbidden() throws Exception {
            mockMvc.perform(get("/api/renter-profiles/renter1")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isForbidden());
        }
    }

    @Nested
    @DisplayName("Get Renter Profile By ID")
    class GetRenterProfileByIdTests {

        @Test
        @DisplayName("Should return 404 when profile not found by ID")
        @WithMockUser (username = "admin1", roles = {"ADMIN"})
        void getRenterProfileById_WhenNotFound_ShouldReturnNotFound() throws Exception {
            mockMvc.perform(get("/api/renter-profiles/id/999")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isNotFound());
        }

        @Test
        @DisplayName("Should return 403 when user is not authenticated")
        void getRenterProfileById_WithoutAuthentication_ShouldReturnForbidden() throws Exception {
            mockMvc.perform(get("/api/renter-profiles/id/1")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isForbidden());
        }
    }

    @Nested
    @DisplayName("Update My Renter Profile")
    class UpdateRenterProfileTests {

        @Test
        @DisplayName("Should return 403 when user does not have RENTER role")
        @WithMockUser (username = "admin1", roles = {"ADMIN"})
        void updateRenterProfile_WithoutRenterRole_ShouldReturnForbidden() throws Exception {
            mockMvc.perform(multipart("/api/renter-profiles")
                            .param("typeLicense", "C")
                            .with(request -> {
                                request.setMethod("PUT");
                                return request;
                            })
                            .contentType(MediaType.MULTIPART_FORM_DATA))
                    .andExpect(status().isForbidden());
        }
    }

    @Nested
    @DisplayName("Delete My Renter Profile")
    class DeleteMyRenterProfileTests {

        @Test
        @DisplayName("Should return 403 when user does not have RENTER role")
        @WithMockUser (username = "admin1", roles = {"ADMIN"})
        void deleteMyRenterProfile_WithoutRenterRole_ShouldReturnForbidden() throws Exception {
            mockMvc.perform(delete("/api/renter-profiles/me")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isForbidden());
        }

        @Test
        @DisplayName("Should return 403 when user is not authenticated")
        void deleteMyRenterProfile_WithoutAuthentication_ShouldReturnForbidden() throws Exception {
            mockMvc.perform(delete("/api/renter-profiles/me")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isForbidden());
        }
    }

    @Nested
    @DisplayName("Delete Renter Profile By ID")
    class DeleteRenterProfileByIdTests {

        @Test
        @DisplayName("Should return 404 when profile not found by ID")
        @WithMockUser (username = "admin1", roles = {"ADMIN"})
        void deleteRenterProfileById_WhenNotFound_ShouldReturnNotFound() throws Exception {
            mockMvc.perform(delete("/api/renter-profiles/id/999")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isNotFound());
        }

        @Test
        @DisplayName("Should return 403 when user is not authenticated")
        void deleteRenterProfileById_WithoutAuthentication_ShouldReturnForbidden() throws Exception {
            mockMvc.perform(delete("/api/renter-profiles/id/1")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isForbidden());
        }
    }
}
