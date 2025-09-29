package com.more_than_code.go_con_coche.vehicle_rental_offer.dtos;

import java.time.LocalDateTime;
import java.util.List;

public record AvailableOfferResponse(
        Long offerId,
        Long vehicleId,
        String vehicleModel,
        String vehicleBrand,
        Long locationId,
        LocalDateTime startDateTime,
        LocalDateTime endDateTime,
        double priceHour,
        List<SlotInfo> availableSlots
) {}