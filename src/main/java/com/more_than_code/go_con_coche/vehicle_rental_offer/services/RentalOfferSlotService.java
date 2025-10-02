package com.more_than_code.go_con_coche.vehicle_rental_offer.services;

import com.more_than_code.go_con_coche.vehicle_rental_offer.models.RentalOfferSlot;
import com.more_than_code.go_con_coche.vehicle_rental_offer.repositories.RentalOfferSlotRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RentalOfferSlotService {
    private final RentalOfferSlotRepository slotRepository;

    public List<RentalOfferSlot> getSlotsWithinPeriod(Long offerId, LocalDateTime from, LocalDateTime to) {
        return slotRepository.findSlotsWithinPeriod(offerId, from, to);
    }
}
