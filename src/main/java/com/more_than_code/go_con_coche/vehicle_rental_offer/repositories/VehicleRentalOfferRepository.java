package com.more_than_code.go_con_coche.vehicle_rental_offer.repositories;

import com.more_than_code.go_con_coche.vehicle_rental_offer.models.VehicleRentalOffer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VehicleRentalOfferRepository extends JpaRepository<VehicleRentalOffer, Long> {
}
