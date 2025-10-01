package com.more_than_code.go_con_coche.vehicle_reservation.dtos;

import jakarta.validation.constraints.*;

import java.time.LocalDateTime;

public record VehicleReservationRequest(
        @NotNull(message = "Rental Offer ID is required")
        Long offerId,
        @NotNull(message = "Start date-time is required")
        @Future(message = "Start date-time must be in the future")
        LocalDateTime startDateTime,
        @NotNull(message = "End date-time is required")
        @Future(message = "End date-time must be in the future")
        LocalDateTime endDateTime,
        @NotNull(message = "Number of travellers is required")
        @Min(value = 1, message = "At least one traveller must be included")
        Integer travellerNumber
) {
}