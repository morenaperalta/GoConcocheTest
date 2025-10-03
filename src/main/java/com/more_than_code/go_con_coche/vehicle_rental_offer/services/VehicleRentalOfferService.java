package com.more_than_code.go_con_coche.vehicle_rental_offer.services;

import com.more_than_code.go_con_coche.vehicle_rental_offer.dtos.RentalOfferRequest;
import com.more_than_code.go_con_coche.vehicle_rental_offer.dtos.RentalOfferResponse;

import java.util.List;

public interface VehicleRentalOfferService {
    RentalOfferResponse createRenterOffer(RentalOfferRequest rentalOfferRequest);
    List<RentalOfferResponse> getMyRentalOffers();
    void deleteRentalOffer(Long id);
}