package com.more_than_code.go_con_coche.vehicle_rental_offer.dtos.search;

import java.time.LocalDateTime;

public record SlotInfo(
        LocalDateTime slotStart,
        LocalDateTime slotEnd
) {}
