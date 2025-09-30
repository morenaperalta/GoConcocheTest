package com.more_than_code.go_con_coche.vehicle_rental_offer.controllers;

import com.more_than_code.go_con_coche.vehicle_rental_offer.dtos.RentalOfferRequest;
import com.more_than_code.go_con_coche.vehicle_rental_offer.dtos.RentalOfferResponse;
import com.more_than_code.go_con_coche.vehicle_rental_offer.services.VehicleRentalOfferServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vehicle-rental-offers")
@RequiredArgsConstructor
public class VehicleRentalOfferController {
    private final VehicleRentalOfferServiceImpl rentalOfferService;

    @PostMapping("")
    public ResponseEntity<RentalOfferResponse> createRentalOffer(@Valid @RequestBody RentalOfferRequest rentalOfferRequest) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(rentalOfferService.createRenterOffer(rentalOfferRequest));
    }

    @GetMapping("/my-offers")
    public ResponseEntity<List<RentalOfferResponse>> getMyRentalOffers(){
        return ResponseEntity.ok()
                .body(rentalOfferService.getMyRentalOffers());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRentalOffer(@PathVariable Long id){
        rentalOfferService.deleteRentalOffer(id);
        return ResponseEntity.noContent().build();
    }
}
