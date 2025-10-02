package com.more_than_code.go_con_coche.vehicle_rental_offer.services;

import com.more_than_code.go_con_coche.global.EntityNotFoundException;
import com.more_than_code.go_con_coche.location.Location;
import com.more_than_code.go_con_coche.location.services.LocationServiceImpl;
import com.more_than_code.go_con_coche.owner_profile.OwnerProfile;
import com.more_than_code.go_con_coche.owner_profile.service.OwnerProfileService;
import com.more_than_code.go_con_coche.registered_user.RegisteredUser;
import com.more_than_code.go_con_coche.registered_user.services.UserAuthService;
import com.more_than_code.go_con_coche.vehicle.dtos.VehicleOfferResponse;
import com.more_than_code.go_con_coche.vehicle.models.FuelTypeCar;
import com.more_than_code.go_con_coche.vehicle.models.Seater;
import com.more_than_code.go_con_coche.vehicle.models.Vehicle;
import com.more_than_code.go_con_coche.vehicle.services.VehicleServiceImpl;
import com.more_than_code.go_con_coche.vehicle_rental_offer.dtos.RentalOfferMapper;
import com.more_than_code.go_con_coche.vehicle_rental_offer.dtos.RentalOfferRequest;
import com.more_than_code.go_con_coche.vehicle_rental_offer.dtos.RentalOfferResponse;
import com.more_than_code.go_con_coche.vehicle_rental_offer.models.VehicleRentalOffer;
import com.more_than_code.go_con_coche.vehicle_rental_offer.repositories.VehicleRentalOfferRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.access.AccessDeniedException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VehicleRentalOfferServiceImplTest {
    @Mock
    private VehicleRentalOfferRepository offerRepository;

    @Mock
    private RentalOfferMapper rentalOfferMapper;

    @Mock
    private VehicleServiceImpl vehicleService;

    @Mock
    private LocationServiceImpl locationService;

    @Mock
    private OwnerProfileService ownerProfileService;

    @Mock
    private UserAuthService userService;

    @InjectMocks
    private VehicleRentalOfferServiceImpl service;

    private Vehicle vehicle;
    private OwnerProfile ownerProfile;
    private RegisteredUser user;
    private Location location;
    private VehicleOfferResponse vehicleOfferResponse;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    @BeforeEach
    void setUp() {
        user = new RegisteredUser();
        user.setId(1L);
        ownerProfile = OwnerProfile.builder()
                .id(10L).registeredUser(user)
                .imageURL("img").build();
        vehicle = Vehicle.builder()
                .id(100L)
                .vin("VIN123")
                .plateNumber("ABC123")
                .insuranceNumber("INS123")
                .brand("BMW")
                .model("X5")
                .year(2020)
                .color("Black")
                .fuelTypeCar(FuelTypeCar.PETROL)
                .seater(Seater.SEDAN).fuelConsumption("8L/100km")
                .imageUrl("img.png")
                .owner(ownerProfile)
                .build();
        location = new Location();
        location.setId(1L);
        location.setCity("Valencia");
        vehicleOfferResponse = new VehicleOfferResponse(vehicle.getId(),
                vehicle.getModel(),
                vehicle.getBrand(),
                vehicle.getYear(),
                vehicle.getColor(),
                vehicle.getSeater(),
                vehicle.getChildSeatsNumber(),
                vehicle.getFuelTypeCar(),
                vehicle.getFuelConsumption(),
                vehicle.getImageUrl());

        startTime = LocalDateTime.of(2025, 1, 10, 10, 0);
        endTime = LocalDateTime.of(2025, 1, 12, 10, 0);

        lenient().when(userService.getAuthenticatedUser()).thenReturn(user);
        lenient().when(ownerProfileService.getOwnerProfileObj()).thenReturn(ownerProfile);
    }

    @Test
    void createRenterOffer_success() {
        RentalOfferRequest request = new RentalOfferRequest(100L, 1L, LocalDateTime.now().plusDays(1), LocalDateTime.now().plusDays(2), 20.0);
        VehicleRentalOffer entity = new VehicleRentalOffer();
        entity.setStartDateTime(startTime);
        entity.setEndDateTime(endTime);

        when(vehicleService.getVehicleByIdObj(vehicle.getId())).thenReturn(vehicle);
        when(locationService.getLocationByIdObj(location.getId())).thenReturn(location);
        when(rentalOfferMapper.toEntity(request)).thenReturn(entity);
        when(offerRepository.existsOverlappingOffer(anyLong(), any(), any())).thenReturn(false);
        when(offerRepository.save(any())).thenAnswer(inv -> inv.getArgument(0));

        RentalOfferResponse response = new RentalOfferResponse(1L,
                vehicleOfferResponse,
                "Valencia",
                LocalDateTime.now().plusDays(1),
                LocalDateTime.now().plusDays(2),
                20.0);

        when(rentalOfferMapper.toRentalOfferResponse(any())).thenReturn(response);
        RentalOfferResponse result = service.createRenterOffer(request);
        assertNotNull(result);
        verify(offerRepository).save(any(VehicleRentalOffer.class));
        verify(rentalOfferMapper).toRentalOfferResponse(any());
    }

    @Test void createRenterOffer_invalidDates_shouldThrow() {
        RentalOfferRequest request = new RentalOfferRequest( vehicle.getId(), location.getId(), endTime, startTime,20.0 );
        assertThrows(IllegalArgumentException.class, () -> service.createRenterOffer(request));
    }

    @Test void getMyRentalOffers_success() {
        VehicleRentalOffer offer = new VehicleRentalOffer();
        offer.setOwner(ownerProfile);
        when(offerRepository.findByOwnerId(anyLong())).thenReturn(List.of(offer));
        when(rentalOfferMapper.toRentalOfferResponse(any())).thenReturn(RentalOfferResponse.builder().build());
        List<RentalOfferResponse> result = service.getMyRentalOffers(); assertEquals(1, result.size());
        verify(offerRepository).findByOwnerId(ownerProfile.getId()); }

    @Test void deleteRentalOffer_success() {
        VehicleRentalOffer offer = new VehicleRentalOffer();
        offer.setId(200L); offer.setOwner(ownerProfile);
        when(offerRepository.findById(200L)).thenReturn(Optional.of(offer));
        service.deleteRentalOffer(200L);
        verify(offerRepository).deleteById(200L); }

    @Test void deleteRentalOffer_wrongOwner_shouldThrow() {
        VehicleRentalOffer offer = new VehicleRentalOffer();
        offer.setId(200L); offer.setOwner(OwnerProfile.builder().id(99L).imageURL("img").build());
        when(offerRepository.findById(200L)).thenReturn(Optional.of(offer));
        assertThrows(AccessDeniedException.class, () -> service.deleteRentalOffer(200L)); }

    @Test void deleteRentalOffer_notFound_shouldThrow() {
        when(offerRepository.findById(200L)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> service.deleteRentalOffer(200L));
    }
}