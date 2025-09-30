package com.more_than_code.go_con_coche.owner_profile;

import com.more_than_code.go_con_coche.cloudinary.CloudinaryService;
import com.more_than_code.go_con_coche.cloudinary.DefaultImageType;
import com.more_than_code.go_con_coche.cloudinary.UploadResult;
import com.more_than_code.go_con_coche.global.EntityAlreadyExistsException;
import com.more_than_code.go_con_coche.global.EntityNotFoundException;
import com.more_than_code.go_con_coche.owner_profile.dtos.OwnerProfileMapper;
import com.more_than_code.go_con_coche.owner_profile.dtos.OwnerProfileRequest;
import com.more_than_code.go_con_coche.owner_profile.dtos.OwnerProfileResponse;
import com.more_than_code.go_con_coche.owner_profile.service.OwnerProfileServiceImpl;
import com.more_than_code.go_con_coche.registered_user.RegisteredUser;
import com.more_than_code.go_con_coche.registered_user.services.UserAuthService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Tests for OwnerProfileServiceImpl")
class OwnerProfileServiceImplTest {

    @Mock
    private OwnerProfileRepository ownerProfileRepository;
    @Mock
    private UserAuthService userAuthService;
    @Mock
    private OwnerProfileMapper ownerProfileMapper;
    @Mock
    private CloudinaryService cloudinaryService;

    @InjectMocks
    private OwnerProfileServiceImpl ownerProfileService;

    private RegisteredUser user;
    private OwnerProfileRequest request;
    private OwnerProfile ownerProfile;
    private OwnerProfileResponse responseDto;
    private UploadResult uploadResult;

    @BeforeEach
    void setUp() {
        user = new RegisteredUser();
        user.setId(1L);
        user.setUsername("ownerUser");

        MockMultipartFile mockFile = new MockMultipartFile(
                "image",
                "test.png",
                "image/png",
                "fake-image-content".getBytes()
        );

        request = new OwnerProfileRequest(mockFile);
        uploadResult = new UploadResult("http://cloudinary.com/img.png", "publicId123");

        ownerProfile = OwnerProfile.builder()
                .registeredUser(user)
                .imageURL(uploadResult.url())
                .publicImageId(uploadResult.publicId())
                .build();

        responseDto = new OwnerProfileResponse(1L, user.getId(), uploadResult.url());
    }

    @Test
    void createOwnerProfile_WhenValidRequest_ShouldReturnResponse() {
        when(userAuthService.getAuthenticatedUser()).thenReturn(user);
        when(ownerProfileRepository.findByRegisteredUserId(user.getId())).thenReturn(Optional.empty());
        when(cloudinaryService.resolveImage(request.image(), DefaultImageType.PROFILE)).thenReturn(uploadResult);
        when(ownerProfileRepository.save(any(OwnerProfile.class))).thenReturn(ownerProfile);
        when(ownerProfileMapper.toResponse(ownerProfile)).thenReturn(responseDto);

        OwnerProfileResponse result = ownerProfileService.createOwnerProfile(request);

        assertNotNull(result);
        assertEquals(responseDto, result);
        verify(ownerProfileRepository).save(ownerProfile);
        verify(cloudinaryService).resolveImage(request.image(), DefaultImageType.PROFILE);
    }

    @Test
    void createOwnerProfile_WhenProfileAlreadyExists_ShouldThrowException() {
        when(userAuthService.getAuthenticatedUser()).thenReturn(user);
        when(ownerProfileRepository.findByRegisteredUserId(user.getId())).thenReturn(Optional.of(ownerProfile));

        assertThrows(EntityAlreadyExistsException.class, () -> ownerProfileService.createOwnerProfile(request));

        verify(ownerProfileRepository, never()).save(any());
        verify(cloudinaryService, never()).resolveImage(any(), any());
    }

    @Test
    void getOwnerProfile_WhenProfileExists_ShouldReturnResponse() {
        when(userAuthService.getAuthenticatedUser()).thenReturn(user);
        when(ownerProfileRepository.findByRegisteredUserId(user.getId())).thenReturn(Optional.of(ownerProfile));
        when(ownerProfileMapper.toResponse(ownerProfile)).thenReturn(responseDto);

        OwnerProfileResponse result = ownerProfileService.getOwnerProfile();

        assertNotNull(result);
        assertEquals(responseDto, result);
    }

    @Test
    void getOwnerProfile_WhenProfileDoesNotExist_ShouldThrowException() {
        when(userAuthService.getAuthenticatedUser()).thenReturn(user);
        when(ownerProfileRepository.findByRegisteredUserId(user.getId())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> ownerProfileService.getOwnerProfile());
    }

    @Test
    void getOwnerProfileObj_WhenProfileExists_ShouldReturnOwnerProfile() {
        when(userAuthService.getAuthenticatedUser()).thenReturn(user);
        when(ownerProfileRepository.findByRegisteredUserId(user.getId())).thenReturn(Optional.of(ownerProfile));

        OwnerProfile result = ownerProfileService.getOwnerProfileObj();

        assertNotNull(result);
        assertEquals(ownerProfile, result);
    }

    @Test
    void getOwnerProfileObj_WhenProfileDoesNotExist_ShouldThrowException() {
        when(userAuthService.getAuthenticatedUser()).thenReturn(user);
        when(ownerProfileRepository.findByRegisteredUserId(user.getId())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> ownerProfileService.getOwnerProfileObj());
    }
}
