package com.more_than_code.go_con_coche.vehicle_rental_offer;

import com.more_than_code.go_con_coche.vehicle_rental_offer.dtos.RentalOfferRequest;
import com.more_than_code.go_con_coche.vehicle_rental_offer.dtos.RentalOfferResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/vehicle-rental-offers")
@RequiredArgsConstructor
public class VehicleRentalOfferController {
    private final VehicleRentalOfferServiceImpl rentalOfferService;

    @PostMapping("")
    public ResponseEntity<RentalOfferResponse> createRentalOffer(@Valid @RequestBody RentalOfferRequest rentalOfferRequest) {
        RentalOfferResponse response = rentalOfferService.createRenterOffer(rentalOfferRequest);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
