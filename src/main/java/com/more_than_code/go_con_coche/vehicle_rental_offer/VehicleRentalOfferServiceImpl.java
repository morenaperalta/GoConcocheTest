package com.more_than_code.go_con_coche.vehicle_rental_offer;

import com.more_than_code.go_con_coche.global.EntityNotFoundException;
import com.more_than_code.go_con_coche.location.Location;
import com.more_than_code.go_con_coche.location.services.LocationServiceImpl;
import com.more_than_code.go_con_coche.owner_profile.OwnerProfile;
import com.more_than_code.go_con_coche.owner_profile.OwnerProfileRepository;
import com.more_than_code.go_con_coche.registered_user.RegisteredUser;
import com.more_than_code.go_con_coche.registered_user.services.UserAuthService;
import com.more_than_code.go_con_coche.vehicle.models.Vehicle;
import com.more_than_code.go_con_coche.vehicle.services.VehicleServiceImpl;
import com.more_than_code.go_con_coche.vehicle_rental_offer.dtos.RentalOfferMapper;
import com.more_than_code.go_con_coche.vehicle_rental_offer.dtos.RentalOfferRequest;
import com.more_than_code.go_con_coche.vehicle_rental_offer.dtos.RentalOfferResponse;
import com.more_than_code.go_con_coche.vehicle_rental_offer.models.RentalOfferSlot;
import com.more_than_code.go_con_coche.vehicle_rental_offer.models.VehicleRentalOffer;
import com.more_than_code.go_con_coche.vehicle_rental_offer.repositories.RentalOfferSlotRepository;
import com.more_than_code.go_con_coche.vehicle_rental_offer.repositories.VehicleRentalOfferRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VehicleRentalOfferServiceImpl implements VehicleRentalOfferService {
    private final VehicleRentalOfferRepository offerRepository;
    private final RentalOfferMapper rentalOfferMapper;
    private final VehicleServiceImpl vehicleService;
    private final LocationServiceImpl locationService;
    private final OwnerProfileRepository ownerProfileRepository;
    private final UserAuthService userService;

    @Override
    public RentalOfferResponse createRenterOffer(RentalOfferRequest rentalOfferRequest) {
        if (!rentalOfferRequest.endDateTime().isAfter(rentalOfferRequest.startDateTime())) {
            throw new IllegalArgumentException("End date-time must be after start date-time");
        }
        RegisteredUser owner = userService.getAuthenticatedUser();
        OwnerProfile ownerProfile = ownerProfileRepository.findByRegisteredUserId(owner.getId())
                .orElseThrow(() -> new EntityNotFoundException("OwnerProfile", "user", owner.getUsername()));
        Vehicle vehicle = vehicleService.getVehicleByIdObj(rentalOfferRequest.vehicleId());
        Location location = locationService.getLocationByIdObj(rentalOfferRequest.locationId());

        if (offerRepository.existsOverlappingOffer(vehicle.getId(), rentalOfferRequest.startDateTime(), rentalOfferRequest.endDateTime())) {
            throw new IllegalArgumentException("Vehicle already has an overlapping offer for the selected dates");
        }

        VehicleRentalOffer rentalOffer = rentalOfferMapper.toEntity(rentalOfferRequest);
        rentalOffer.setVehicle(vehicle);
        rentalOffer.setLocation(location);
        rentalOffer.setOwner(ownerProfile);
        rentalOffer.setAvailable(true);
        rentalOffer.setSlots(generateSlots(rentalOffer));
        VehicleRentalOffer savedOffer = offerRepository.save(rentalOffer);
        return rentalOfferMapper.toRentalOfferResponse(savedOffer);
    }

    private List<RentalOfferSlot> generateSlots(VehicleRentalOffer offer) {
        List<RentalOfferSlot> slots = new ArrayList<>();

        LocalDateTime current = offer.getStartDateTime();
        while (current.isBefore(offer.getEndDateTime())) {
            LocalDateTime slotEnd = current.plusHours(8);
            if (slotEnd.isAfter(offer.getEndDateTime())) {
                slotEnd = offer.getEndDateTime();
            }
            RentalOfferSlot slot = RentalOfferSlot.builder()
                    .offer(offer)
                    .slotStart(current)
                    .slotEnd(slotEnd)
                    .isAvailable(true)
                    .build();
            slots.add(slot);
            current = slotEnd;
        }
        return slots;
    }
}
