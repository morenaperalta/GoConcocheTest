package com.more_than_code.go_con_coche.vehicle_rental_offer.dtos;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record RentalOfferRequest(@NotNull(message = "Vehicle ID is required")
                                 Long vehicleId,
                                 @NotNull(message = "Location ID is required")
                                 Long locationId,
                                 @NotNull(message = "Start date-time cannot be null")
                                 @Future(message = "Start date-time must be in the future")
                                 LocalDateTime startDateTime,
                                 @NotNull(message = "End date-time cannot be null")
                                 @Future(message = "End date-time must be in the future")
                                 LocalDateTime endDateTime,
                                 @Min(value = 1, message = "Travellers number should be more then 0")
                                 int travellersNumber,
                                 @DecimalMin(value = "0.01", inclusive = true, message = "Price per hour should be more then 0")
                                 double priceHour) {
}