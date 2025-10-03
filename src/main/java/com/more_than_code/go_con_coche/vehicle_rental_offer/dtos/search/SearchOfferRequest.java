package com.more_than_code.go_con_coche.vehicle_rental_offer.dtos.search;

import jakarta.validation.constraints.*;

import java.time.LocalDateTime;

public record SearchOfferRequest(@NotNull(message = "Vehicle ID is required")
                                 Long locationId,
                                 @NotNull(message = "Start date-time cannot be null")
                                 @Future(message = "Start date-time must be in the future")
                                 LocalDateTime startDateTime,
                                 @NotNull(message = "Start date-time cannot be null")
                                 @Future(message = "Start date-time must be in the future")
                                 LocalDateTime endDateTime) {
}