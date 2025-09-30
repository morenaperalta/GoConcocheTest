package com.more_than_code.go_con_coche.vehicle_reservation.services;

import com.more_than_code.go_con_coche.email.EmailService;
import com.more_than_code.go_con_coche.global.EntityNotFoundException;
import com.more_than_code.go_con_coche.global.UnauthorizedActionException;
import com.more_than_code.go_con_coche.renter_profile.models.RenterProfile;
import com.more_than_code.go_con_coche.renter_profile.services.RenterProfileService;
import com.more_than_code.go_con_coche.vehicle.models.Seater;
import com.more_than_code.go_con_coche.vehicle_rental_offer.models.RentalOfferSlot;
import com.more_than_code.go_con_coche.vehicle_rental_offer.models.VehicleRentalOffer;
import com.more_than_code.go_con_coche.vehicle_rental_offer.repositories.RentalOfferSlotRepository;
import com.more_than_code.go_con_coche.vehicle_rental_offer.repositories.VehicleRentalOfferRepository;
import com.more_than_code.go_con_coche.vehicle_rental_offer.services.RentalOfferSlotService;
import com.more_than_code.go_con_coche.vehicle_reservation.VehicleReservationRepository;
import com.more_than_code.go_con_coche.vehicle_reservation.dtos.VehicleReservationMapper;
import com.more_than_code.go_con_coche.vehicle_reservation.dtos.VehicleReservationRequest;
import com.more_than_code.go_con_coche.vehicle_reservation.dtos.VehicleReservationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VehicleReservationServiceImpl implements VehicleReservationService{

    private final VehicleReservationRepository reservationRepository;
    private final VehicleRentalOfferRepository offerRepository;
    private final RenterProfileService renterProfileService;
    private final RentalOfferSlotService slotService;
    private final RentalOfferSlotRepository slotRepository;
    private final VehicleReservationMapper reservationMapper;
    private final EmailService emailService;

    private int getVehicleCapacity (Seater seater) {
        return switch (seater) {
            case SMART -> 2;
            case SEDAN -> 4;
            case SUV -> 5;
            case VAN -> 7;
        };
    }

    @Override
    @Transactional
    public VehicleReservationResponse createReservation(VehicleReservationRequest request) {
        if(!request.endDateTime().isAfter(request.startDateTime())) {
            throw new IllegalArgumentException("End date-time must be after start date-time");
        }

        RenterProfile renterProfile = renterProfileService.getRenterProfileObj();

        VehicleRentalOffer offer = offerRepository.findById(request.offerId())
                .orElseThrow(() -> new EntityNotFoundException("VehicleRentalOffer", "id", String.valueOf(request.offerId())));
        if (request.startDateTime().isBefore(offer.getStartDateTime()) || request.endDateTime().isAfter(offer.getEndDateTime())) {
            throw new IllegalArgumentException("Reservation time must be fully within the offer period.");
        }

        int vehicleCapacity = getVehicleCapacity(offer.getVehicle().getSeater());
        if (request.travellerNumber() > vehicleCapacity) {
            throw new IllegalArgumentException(String.format("The number of travellers (%d) exceeds the vehicle's seating capacity (%d).", request.travellerNumber(), vehicleCapacity));
        }

        List<RentalOfferSlot> slotsToReserve = slotService.getSlotsWithinPeriod(
                request.offerId(), 
                request.startDateTime(),
                request.endDateTime()
        );
        
        if (slotsToReserve.isEmpty()) {
            throw new IllegalArgumentException("The requested reservation period does not overlap with any available slots.");
        }
        
        for (RentalOfferSlot slot : slotsToReserve) {
            if (slot.getSlotStart().isBefore(request.endDateTime()) && slot.getSlotEnd().isAfter(request.startDateTime())) {
                if (!slot.isAvailable()) {
                    throw new UnauthorizedActionException("make a reservation, time slot already reserved", "VehicleRentalOffer", offer.getId().toString());
                }
            }
        }
    }
}