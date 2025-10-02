package com.more_than_code.go_con_coche.renter_profile;

import com.more_than_code.go_con_coche.cloudinary.CloudinaryService;
import com.more_than_code.go_con_coche.cloudinary.DefaultImageType;
import com.more_than_code.go_con_coche.cloudinary.UploadResult;
import com.more_than_code.go_con_coche.global.EntityAlreadyExistsException;
import com.more_than_code.go_con_coche.global.EntityNotFoundException;
import com.more_than_code.go_con_coche.registered_user.RegisteredUser;
import com.more_than_code.go_con_coche.registered_user.services.UserAuthService;
import com.more_than_code.go_con_coche.renter_profile.dtos.RenterProfileMapper;
import com.more_than_code.go_con_coche.renter_profile.dtos.RenterProfileRequest;
import com.more_than_code.go_con_coche.renter_profile.dtos.RenterProfileUpdateRequest;
import com.more_than_code.go_con_coche.renter_profile.dtos.RenterProfileResponse;
import com.more_than_code.go_con_coche.renter_profile.models.RenterProfile;
import com.more_than_code.go_con_coche.renter_profile.models.TypeLicense;
import com.more_than_code.go_con_coche.renter_profile.services.RenterProfileServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Tests for RenterProfileService")
class RenterProfileServiceImplTest {

    @Mock
    private RenterProfileRepository renterProfileRepository;
    @Mock
    private UserAuthService userAuthService;
    @Mock
    private RenterProfileMapper renterProfileMapper;
    @Mock
    private CloudinaryService cloudinaryService;

    @InjectMocks
    private RenterProfileServiceImpl renterProfileService;

    private RegisteredUser user;
    private RenterProfileRequest request;
    private RenterProfileUpdateRequest updateRequest;
    private RenterProfile renterProfile;
    private RenterProfileResponse responseDto;
    private UploadResult uploadResult;
    private MockMultipartFile mockFile;

    @BeforeEach
    void setUp() {
        user = new RegisteredUser();
        user.setUsername("testUser");
        user.setId(5L);

        mockFile = new MockMultipartFile("image", "test.png", "image/png", "fake-image-content".getBytes());

        uploadResult = new UploadResult("http://cloudinary.com/img.png", "publicId123");
        request = new RenterProfileRequest(TypeLicense.B, "abc123456", LocalDate.of(2026, 11, 26), mockFile);
        updateRequest = new RenterProfileUpdateRequest(TypeLicense.C, null, null, null);

        renterProfile = RenterProfile.builder().id(1L).registeredUser(user).typeLicense(TypeLicense.B).licenseNumber("abc123456").expiredDate(LocalDate.of(2026, 11, 26)).imageURL("http://cloudinary.com/img.png").publicImageId("publicId123").build();

        responseDto = new RenterProfileResponse(1L, 5L, "B", "abc123456", LocalDate.of(2026, 11, 26), "http://cloudinary.com/img.png");
    }

    @Nested
    @DisplayName("createRenterProfile() tests")
    class CreateRenterProfileTests {

        @Test
        @DisplayName("Should create renter profile successfully when valid request")
        void createRenterProfile_WhenValidRequest_ShouldReturnResponse() {

            when(userAuthService.getAuthenticatedUser()).thenReturn(user);
            when(renterProfileRepository.existsByRegisteredUser(user)).thenReturn(false);
            when(cloudinaryService.resolveImage(request.image(), DefaultImageType.PROFILE)).thenReturn(uploadResult);
            when(renterProfileRepository.save(any(RenterProfile.class))).thenReturn(renterProfile);
            when(renterProfileMapper.toResponse(renterProfile)).thenReturn(responseDto);

            RenterProfileResponse result = renterProfileService.createRenterProfile(request);

            assertNotNull(result);
            assertEquals(responseDto.id(), result.id());
            assertEquals(responseDto.registeredUser(), result.registeredUser());
            assertEquals(responseDto.typeLicense(), result.typeLicense());
            assertEquals(responseDto.licenseNumber(), result.licenseNumber());
            assertEquals(responseDto.expiredDate(), result.expiredDate());
            assertEquals(responseDto.imageUrl(), result.imageUrl());

            verify(userAuthService).getAuthenticatedUser();
            verify(renterProfileRepository).existsByRegisteredUser(user);
            verify(cloudinaryService).resolveImage(request.image(), DefaultImageType.PROFILE);
            verify(renterProfileRepository).save(any(RenterProfile.class));
            verify(renterProfileMapper).toResponse(renterProfile);
        }

        @Test
        @DisplayName("Should throw EntityAlreadyExistsException when user already has profile")
        void createRenterProfile_WhenUserAlreadyHasProfile_ShouldThrowException() {

            when(userAuthService.getAuthenticatedUser()).thenReturn(user);
            when(renterProfileRepository.existsByRegisteredUser(user)).thenReturn(true);

            EntityAlreadyExistsException exception = assertThrows(EntityAlreadyExistsException.class, () -> renterProfileService.createRenterProfile(request));

            assertTrue(exception.getMessage().contains("RenterProfile"));
            assertTrue(exception.getMessage().contains("testUser"));

            verify(userAuthService).getAuthenticatedUser();
            verify(renterProfileRepository).existsByRegisteredUser(user);
            verify(renterProfileRepository, never()).save(any());
            verify(cloudinaryService, never()).resolveImage(any(), any());
            verify(renterProfileMapper, never()).toResponse(any());
        }

        @Test
        @DisplayName("Should create profile with null image using default")
        void createRenterProfile_WithNullImage_ShouldUseDefaultImage() {

            RenterProfileRequest requestWithNullImage = new RenterProfileRequest(TypeLicense.B, "abc123456", LocalDate.of(2026, 11, 26), null);

            UploadResult defaultUploadResult = new UploadResult("http://cloudinary.com/default.png", "defaultPublicId");

            RenterProfile profileWithDefaultImage = RenterProfile.builder().id(1L).registeredUser(user).typeLicense(TypeLicense.B).licenseNumber("abc123456").expiredDate(LocalDate.of(2026, 11, 26)).imageURL("http://cloudinary.com/default.png").publicImageId("defaultPublicId").build();

            RenterProfileResponse responseWithDefault = new RenterProfileResponse(1L, 5L, "B", "abc123456", LocalDate.of(2026, 11, 26), "http://cloudinary.com/default.png");

            when(userAuthService.getAuthenticatedUser()).thenReturn(user);
            when(renterProfileRepository.existsByRegisteredUser(user)).thenReturn(false);
            when(cloudinaryService.resolveImage(null, DefaultImageType.PROFILE)).thenReturn(defaultUploadResult);
            when(renterProfileRepository.save(any(RenterProfile.class))).thenReturn(profileWithDefaultImage);
            when(renterProfileMapper.toResponse(profileWithDefaultImage)).thenReturn(responseWithDefault);

            RenterProfileResponse result = renterProfileService.createRenterProfile(requestWithNullImage);

            assertNotNull(result);
            assertEquals("http://cloudinary.com/default.png", result.imageUrl());

            verify(cloudinaryService).resolveImage(null, DefaultImageType.PROFILE);
            verify(renterProfileRepository).save(any(RenterProfile.class));
        }
    }

    @Nested
    @DisplayName("getAllRenterProfiles() tests")
    class GetAllRenterProfilesTests {

        @Test
        @DisplayName("Should return all renter profiles")
        void getAllRenterProfiles_ShouldReturnListOfProfiles() {

            RenterProfile profile1 = RenterProfile.builder().id(1L).registeredUser(user).typeLicense(TypeLicense.B).licenseNumber("abc123456").expiredDate(LocalDate.of(2026, 11, 26)).imageURL("http://cloudinary.com/img1.png").build();

            RenterProfile profile2 = RenterProfile.builder().id(2L).registeredUser(user).typeLicense(TypeLicense.C).licenseNumber("xyz789012").expiredDate(LocalDate.of(2027, 5, 15)).imageURL("http://cloudinary.com/img2.png").build();

            List<RenterProfile> profiles = Arrays.asList(profile1, profile2);

            RenterProfileResponse response1 = new RenterProfileResponse(1L, 5L, "B", "abc123456", LocalDate.of(2026, 11, 26), "http://cloudinary.com/img1.png");

            RenterProfileResponse response2 = new RenterProfileResponse(2L, 5L, "C", "xyz789012", LocalDate.of(2027, 5, 15), "http://cloudinary.com/img2.png");

            when(renterProfileRepository.findAll()).thenReturn(profiles);
            when(renterProfileMapper.toResponse(profile1)).thenReturn(response1);
            when(renterProfileMapper.toResponse(profile2)).thenReturn(response2);

            List<RenterProfileResponse> result = renterProfileService.getAllRenterProfiles();

            assertNotNull(result);
            assertEquals(2, result.size());
            assertEquals(response1.id(), result.get(0).id());
            assertEquals(response2.id(), result.get(1).id());

            verify(renterProfileRepository).findAll();
            verify(renterProfileMapper, times(2)).toResponse(any(RenterProfile.class));
        }

        @Test
        @DisplayName("Should return empty list when no profiles exist")
        void getAllRenterProfiles_WhenNoProfiles_ShouldReturnEmptyList() {

            when(renterProfileRepository.findAll()).thenReturn(List.of());

            List<RenterProfileResponse> result = renterProfileService.getAllRenterProfiles();

            assertNotNull(result);
            assertTrue(result.isEmpty());

            verify(renterProfileRepository).findAll();
            verify(renterProfileMapper, never()).toResponse(any());
        }
    }

    @Nested
    @DisplayName("getMyRenterProfile() tests")
    class GetMyRenterProfileTests {

        @Test
        @DisplayName("Should return own renter profile when user is authenticated")
        void getMyRenterProfile_WhenUserAuthenticated_ShouldReturnProfile() {

            when(userAuthService.getAuthenticatedUser()).thenReturn(user);
            when(renterProfileRepository.findByRegisteredUserId(user.getId())).thenReturn(Optional.of(renterProfile));
            when(renterProfileMapper.toResponse(renterProfile)).thenReturn(responseDto);

            RenterProfileResponse result = renterProfileService.getMyRenterProfile();

            assertNotNull(result);
            assertEquals(responseDto.id(), result.id());
            assertEquals(responseDto.registeredUser(), result.registeredUser());
            assertEquals(responseDto.typeLicense(), result.typeLicense());
            assertEquals(responseDto.licenseNumber(), result.licenseNumber());
            assertEquals(responseDto.expiredDate(), result.expiredDate());
            assertEquals(responseDto.imageUrl(), result.imageUrl());

            verify(userAuthService).getAuthenticatedUser();
            verify(renterProfileRepository).findByRegisteredUserId(user.getId());
            verify(renterProfileMapper).toResponse(renterProfile);
        }

        @Test
        @DisplayName("Should throw EntityNotFoundException when own profile does not exist")
        void getMyRenterProfile_WhenProfileNotFound_ShouldThrowException() {

            when(userAuthService.getAuthenticatedUser()).thenReturn(user);
            when(renterProfileRepository.findByRegisteredUserId(user.getId())).thenReturn(Optional.empty());

            EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> renterProfileService.getMyRenterProfile());

            assertTrue(exception.getMessage().contains("RenterProfile"));
            assertTrue(exception.getMessage().contains("5"));

            verify(userAuthService).getAuthenticatedUser();
            verify(renterProfileRepository).findByRegisteredUserId(user.getId());
            verify(renterProfileMapper, never()).toResponse(any());
        }
    }

    @Nested
    @DisplayName("getRenterProfileByUsername() tests")
    class GetRenterProfileByUsernameTests {

        @Test
        @DisplayName("Should return renter profile by username when profile exists")
        void getRenterProfileByUsername_WhenProfileExists_ShouldReturnProfile() {

            String username = "testUser";

            when(renterProfileRepository.findByRegisteredUserUsername(username)).thenReturn(Optional.of(renterProfile));
            when(renterProfileMapper.toResponse(renterProfile)).thenReturn(responseDto);

            RenterProfileResponse result = renterProfileService.getRenterProfileByUsername(username);

            assertNotNull(result);
            assertEquals(responseDto.id(), result.id());
            assertEquals(responseDto.registeredUser(), result.registeredUser());
            assertEquals(responseDto.typeLicense(), result.typeLicense());
            assertEquals(responseDto.licenseNumber(), result.licenseNumber());
            assertEquals(responseDto.expiredDate(), result.expiredDate());
            assertEquals(responseDto.imageUrl(), result.imageUrl());

            verify(renterProfileRepository).findByRegisteredUserUsername(username);
            verify(renterProfileMapper).toResponse(renterProfile);
        }

        @Test
        @DisplayName("Should throw EntityNotFoundException when profile not found by username")
        void getRenterProfileByUsername_WhenProfileNotFound_ShouldThrowException() {

            String username = "nonExistentUser";

            when(renterProfileRepository.findByRegisteredUserUsername(username)).thenReturn(Optional.empty());

            EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> renterProfileService.getRenterProfileByUsername(username));

            assertTrue(exception.getMessage().contains("RenterProfile"));
            assertTrue(exception.getMessage().contains("nonExistentUser"));

            verify(renterProfileRepository).findByRegisteredUserUsername(username);
            verify(renterProfileMapper, never()).toResponse(any());
        }
    }

    @Nested
    @DisplayName("getRenterProfileById() tests")
    class GetRenterProfileByIdTests {

        @Test
        @DisplayName("Should return renter profile by ID when profile exists")
        void getRenterProfileById_WhenProfileExists_ShouldReturnProfile() {

            Long profileId = 5L;

            when(renterProfileRepository.findByRegisteredUserId(profileId)).thenReturn(Optional.of(renterProfile));
            when(renterProfileMapper.toResponse(renterProfile)).thenReturn(responseDto);

            RenterProfileResponse result = renterProfileService.getRenterProfileById(profileId);

            assertNotNull(result);
            assertEquals(responseDto.id(), result.id());
            assertEquals(responseDto.registeredUser(), result.registeredUser());
            assertEquals(responseDto.typeLicense(), result.typeLicense());
            assertEquals(responseDto.licenseNumber(), result.licenseNumber());
            assertEquals(responseDto.expiredDate(), result.expiredDate());
            assertEquals(responseDto.imageUrl(), result.imageUrl());

            verify(renterProfileRepository).findByRegisteredUserId(profileId);
            verify(renterProfileMapper).toResponse(renterProfile);
        }

        @Test
        @DisplayName("Should throw EntityNotFoundException when profile not found by ID")
        void getRenterProfileById_WhenProfileNotFound_ShouldThrowException() {

            Long profileId = 999L;

            when(renterProfileRepository.findByRegisteredUserId(profileId)).thenReturn(Optional.empty());

            EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> renterProfileService.getRenterProfileById(profileId));

            assertTrue(exception.getMessage().contains("RenterProfile"));
            assertTrue(exception.getMessage().contains("999"));

            verify(renterProfileRepository).findByRegisteredUserId(profileId);
            verify(renterProfileMapper, never()).toResponse(any());
        }
    }

    @Nested
    @DisplayName("deleteMyRenterProfile() tests")
    class DeleteMyRenterProfileTests {

        @Test
        @DisplayName("Should delete own renter profile successfully")
        void deleteMyRenterProfile_WhenProfileExists_ShouldDeleteProfile() {

            when(userAuthService.getAuthenticatedUser()).thenReturn(user);
            when(renterProfileRepository.findByRegisteredUserId(user.getId())).thenReturn(Optional.of(renterProfile));

            renterProfileService.deleteMyRenterProfile();

            verify(userAuthService).getAuthenticatedUser();
            verify(renterProfileRepository).findByRegisteredUserId(user.getId());
            verify(cloudinaryService).delete(renterProfile.getPublicImageId());
            verify(renterProfileRepository).delete(renterProfile);
        }

        @Test
        @DisplayName("Should delete profile without deleting image when publicImageId is null")
        void deleteMyRenterProfile_WhenNoPublicImageId_ShouldDeleteProfileWithoutDeletingImage() {

            RenterProfile profileWithoutImage = RenterProfile.builder()
                    .id(1L)
                    .registeredUser(user)
                    .typeLicense(TypeLicense.B)
                    .licenseNumber("abc123456")
                    .expiredDate(LocalDate.of(2026, 11, 26))
                    .imageURL("http://cloudinary.com/img.png")
                    .publicImageId(null)
                    .build();

            when(userAuthService.getAuthenticatedUser()).thenReturn(user);
            when(renterProfileRepository.findByRegisteredUserId(user.getId())).thenReturn(Optional.of(profileWithoutImage));

            renterProfileService.deleteMyRenterProfile();

            verify(userAuthService).getAuthenticatedUser();
            verify(renterProfileRepository).findByRegisteredUserId(user.getId());
            verify(cloudinaryService, never()).delete(any());
            verify(renterProfileRepository).delete(profileWithoutImage);
        }

        @Test
        @DisplayName("Should throw EntityNotFoundException when own profile not found")
        void deleteMyRenterProfile_WhenProfileNotFound_ShouldThrowException() {

            when(userAuthService.getAuthenticatedUser()).thenReturn(user);
            when(renterProfileRepository.findByRegisteredUserId(user.getId())).thenReturn(Optional.empty());

            EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> renterProfileService.deleteMyRenterProfile());

            assertTrue(exception.getMessage().contains("RenterProfile"));
            assertTrue(exception.getMessage().contains("5"));

            verify(userAuthService).getAuthenticatedUser();
            verify(renterProfileRepository).findByRegisteredUserId(user.getId());
            verify(cloudinaryService, never()).delete(any());
            verify(renterProfileRepository, never()).delete(any());
        }
    }

    @Nested
    @DisplayName("deleteRenterProfileById() tests")
    class DeleteRenterProfileByIdTests {

        @Test
        @DisplayName("Should delete renter profile by ID successfully")
        void deleteRenterProfileById_WhenProfileExists_ShouldDeleteProfile() {

            Long profileId = 1L;

            when(renterProfileRepository.findById(profileId)).thenReturn(Optional.of(renterProfile));

            renterProfileService.deleteRenterProfileById(profileId);

            verify(renterProfileRepository).findById(profileId);
            verify(cloudinaryService).delete(renterProfile.getPublicImageId());
            verify(renterProfileRepository).delete(renterProfile);
        }

        @Test
        @DisplayName("Should delete profile by ID without deleting image when publicImageId is null")
        void deleteRenterProfileById_WhenNoPublicImageId_ShouldDeleteProfileWithoutDeletingImage() {

            Long profileId = 1L;

            RenterProfile profileWithoutImage = RenterProfile.builder()
                    .id(1L)
                    .registeredUser(user)
                    .typeLicense(TypeLicense.B)
                    .licenseNumber("abc123456")
                    .expiredDate(LocalDate.of(2026, 11, 26))
                    .imageURL("http://cloudinary.com/img.png")
                    .publicImageId(null)
                    .build();

            when(renterProfileRepository.findById(profileId)).thenReturn(Optional.of(profileWithoutImage));

            renterProfileService.deleteRenterProfileById(profileId);

            verify(renterProfileRepository).findById(profileId);
            verify(cloudinaryService, never()).delete(any());
            verify(renterProfileRepository).delete(profileWithoutImage);
        }

        @Test
        @DisplayName("Should throw EntityNotFoundException when profile not found by ID")
        void deleteRenterProfileById_WhenProfileNotFound_ShouldThrowException() {

            Long profileId = 999L;

            when(renterProfileRepository.findById(profileId)).thenReturn(Optional.empty());

            EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> renterProfileService.deleteRenterProfileById(profileId));

            assertTrue(exception.getMessage().contains("RenterProfile"));
            assertTrue(exception.getMessage().contains("999"));

            verify(renterProfileRepository).findById(profileId);
            verify(cloudinaryService, never()).delete(any());
            verify(renterProfileRepository, never()).delete(any());
        }
    }
}