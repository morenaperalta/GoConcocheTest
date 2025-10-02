package com.more_than_code.go_con_coche.vehicle_rental_offer.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

@Schema(description = "Request to create a rental offer for a vehicle")
public record RentalOfferRequest(
        @NotNull(message = "Vehicle ID is required") @Schema(description = "ID of the vehicle to rent", example = "1")
        Long vehicleId,

        @NotNull(message = "Location ID is required") @Schema(description = "ID of the location where the vehicle is available", example = "2")
        Long locationId,

        @NotNull(message = "Start date-time cannot be null") @Future(message = "Start date-time must be in the future") @Schema(description = "Rental start date and time", example = "2025-10-02T09:00:00")
        LocalDateTime startDateTime,

        @NotNull(message = "End date-time cannot be null") @Future(message = "End date-time must be in the future") @Schema(description = "Rental end date and time", example = "2025-10-02T17:00:00")
        LocalDateTime endDateTime,

        @DecimalMin(value = "0.01", inclusive = true, message = "Price per hour should be more then 0")         @Schema(description = "Price per hour in euros", example = "15.50")
        double priceHour) {
}