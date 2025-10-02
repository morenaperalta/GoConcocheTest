package com.more_than_code.go_con_coche.vehicle_reservation.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

import java.time.LocalDateTime;

@Schema(description = "Request to create a vehicle reservation")
public record VehicleReservationRequest(
        @NotNull(message = "Rental Offer ID is required")
        @Schema(description = "ID of the rental offer to reserve", example = "10")
        Long offerId,

        @NotNull(message = "Start date-time is required")
        @Future(message = "Start date-time must be in the future")
        @Schema(description = "Reservation start date and time", example = "2025-10-02T10:00:00")
        LocalDateTime startDateTime,

        @NotNull(message = "End date-time is required")
        @Future(message = "End date-time must be in the future")
        @Schema(description = "Reservation end date and time", example = "2025-10-02T18:00:00")
        LocalDateTime endDateTime,

        @NotNull(message = "Number of travellers is required")
        @Min(value = 1, message = "At least one traveller must be included")
        @Schema(description = "Number of travellers for this reservation", example = "2")
        Integer travellerNumber
) {
}