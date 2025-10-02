package com.more_than_code.go_con_coche.owner_profile;

import com.more_than_code.go_con_coche.owner_profile.dtos.OwnerProfileRequest;
import com.more_than_code.go_con_coche.owner_profile.dtos.OwnerProfileResponse;
import com.more_than_code.go_con_coche.owner_profile.service.OwnerProfileService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.extension.ExtendWith;

import java.util.List;

@ExtendWith(MockitoExtension.class)
@DisplayName("Tests for OwnerProfileController")
class OwnerProfileControllerTest {

    @Mock
    private OwnerProfileService ownerProfileService;

    @InjectMocks
    private OwnerProfileController ownerProfileController;

    private OwnerProfileRequest request;
    private OwnerProfileResponse responseDto;

    @BeforeEach
    void setUp() {
        MockMultipartFile mockFile = new MockMultipartFile(
                "image",
                "test.png",
                "image/png",
                "fake-image-content".getBytes()
        );
        request = new OwnerProfileRequest(mockFile);
        responseDto = new OwnerProfileResponse(1L, 1L, "http://cloudinary.com/img.png");
    }

    @Test
    void createOwnerProfile_ShouldReturnCreatedResponse() {
        MockMultipartFile mockFile = new MockMultipartFile("image", "test.png", "image/png", "fake-image-content".getBytes()
        );

        OwnerProfileResponse responseDto = new OwnerProfileResponse(1L, 1L, "http://cloudinary.com/img.png");
        when(ownerProfileService.createOwnerProfile(any(OwnerProfileRequest.class))).thenReturn(responseDto);

        ResponseEntity<OwnerProfileResponse> response = ownerProfileController.createOwnerProfile(mockFile);

        assertNotNull(response);
        assertEquals(201, response.getStatusCodeValue());
        assertEquals(responseDto, response.getBody());
        verify(ownerProfileService).createOwnerProfile(any(OwnerProfileRequest.class));
    }

    @Test
    void getOwnerProfile_ShouldReturnOkResponse() {
        when(ownerProfileService.getOwnerProfile()).thenReturn(responseDto);

        ResponseEntity<OwnerProfileResponse> response = ownerProfileController.getOwnerProfile();

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(responseDto, response.getBody());
        verify(ownerProfileService).getOwnerProfile();
    }

    @Test
    void getAllOwnerProfiles_ShouldReturnOkResponse() {
        when(ownerProfileService.getAllOwnerProfiles()).thenReturn(List.of(responseDto));

        ResponseEntity<List<OwnerProfileResponse>> response = ownerProfileController.getAllOwnerProfiles();

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1, response.getBody().size());
        assertEquals(responseDto, response.getBody().get(0));
        verify(ownerProfileService).getAllOwnerProfiles();
    }

    @Test
    void getOwnerProfileById_ShouldReturnOkResponse() {
        when(ownerProfileService.getOwnerProfileById(1L)).thenReturn(responseDto);

        ResponseEntity<OwnerProfileResponse> response = ownerProfileController.getOwnerProfileById(1L);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(responseDto, response.getBody());
        verify(ownerProfileService).getOwnerProfileById(1L);
    }

    @Test
    void updateMyOwnerProfile_ShouldReturnUpdatedResponse() {
        MockMultipartFile mockFile = new MockMultipartFile("image", "test-update.png", "image/png", "fake-image-content".getBytes()
        );

        OwnerProfileResponse responseDto = new OwnerProfileResponse(1L, 1L, "http://cloudinary.com/img-updated.png");
        when(ownerProfileService.updateMyOwnerProfile(any(OwnerProfileRequest.class))).thenReturn(responseDto);

        ResponseEntity<OwnerProfileResponse> response = ownerProfileController.updateMyOwnerProfile(mockFile);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(responseDto, response.getBody());
        verify(ownerProfileService).updateMyOwnerProfile(any(OwnerProfileRequest.class));
    }


    @Test
    void deleteMyOwnerProfile_ShouldReturnNoContent() {
        doNothing().when(ownerProfileService).deleteMyOwnerProfile();

        ResponseEntity<Void> response = ownerProfileController.deleteMyOwnerProfile();

        assertNotNull(response);
        assertEquals(204, response.getStatusCodeValue());
        verify(ownerProfileService).deleteMyOwnerProfile();
    }

    @Test
    void deleteOwnerProfileById_ShouldReturnNoContent() {
        doNothing().when(ownerProfileService).deleteOwnerProfileById(1L);

        ResponseEntity<Void> response = ownerProfileController.deleteOwnerProfileById(1L);

        assertNotNull(response);
        assertEquals(204, response.getStatusCodeValue());
        verify(ownerProfileService).deleteOwnerProfileById(1L);
    }


}
