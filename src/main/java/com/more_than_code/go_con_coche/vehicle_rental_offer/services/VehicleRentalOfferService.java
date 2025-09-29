package com.more_than_code.go_con_coche.vehicle_rental_offer.services;
import com.more_than_code.go_con_coche.vehicle_rental_offer.dtos.RentalOfferRequest;
import com.more_than_code.go_con_coche.vehicle_rental_offer.dtos.RentalOfferResponse;

public interface VehicleRentalOfferService {
    RentalOfferResponse createRenterOffer(RentalOfferRequest rentalOfferRequest);
}
