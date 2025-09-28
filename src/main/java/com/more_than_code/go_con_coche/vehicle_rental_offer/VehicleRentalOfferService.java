package com.more_than_code.go_con_coche.vehicle_rental_offer;

import com.more_than_code.go_con_coche.vehicle_rental_offer.repositories.RentalOfferSlotRepository;
import com.more_than_code.go_con_coche.vehicle_rental_offer.repositories.VehicleRentalOfferRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VehicleRentalOfferService {
    private final VehicleRentalOfferRepository offerRepository;
    private final RentalOfferSlotRepository slotRepository;

}
