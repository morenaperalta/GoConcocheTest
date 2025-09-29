package com.more_than_code.go_con_coche.vehicle_rental_offer.dtos;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

public record SearchOfferRequest(Long locationId,
                                 @NotNull(message = "Start date-time cannot be null")
                                 @Future(message = "Start date-time must be in the future")
                                 LocalDateTime startDateTime,
                                 @NotNull(message = "Start date-time cannot be null")
                                 @Future(message = "Start date-time must be in the future")
                                 LocalDateTime endDateTime) {
}
