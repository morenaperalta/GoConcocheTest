package com.more_than_code.go_con_coche.vehicle_reservation;

import com.more_than_code.go_con_coche.vehicle.dtos.VehicleOfferResponse;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record VehicleReservationResponse(
        Long id,
        String reservationCode,
        Long renterProfileId,
        Long rentalOfferId,
        VehicleOfferResponse vehicleDetails,
        LocalDateTime startDateTime
) {
}