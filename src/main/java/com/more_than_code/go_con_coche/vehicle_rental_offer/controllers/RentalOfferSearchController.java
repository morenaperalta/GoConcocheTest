package com.more_than_code.go_con_coche.vehicle_rental_offer.controllers;

import com.more_than_code.go_con_coche.vehicle_rental_offer.dtos.search.SearchOfferRequest;
import com.more_than_code.go_con_coche.vehicle_rental_offer.dtos.search.SearchOfferResponse;
import com.more_than_code.go_con_coche.vehicle_rental_offer.services.RentalOfferSearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/vehicle-rental-offers")
@RequiredArgsConstructor
public class RentalOfferSearchController {
    private final RentalOfferSearchService searchService;

    @GetMapping("/search")
    public ResponseEntity<List<SearchOfferResponse>> searchOffers(@RequestBody SearchOfferRequest request) {
        List<SearchOfferResponse> offers = searchService.searchAvailableOffers(request.locationId(), request.startDateTime(), request.endDateTime());
        return ResponseEntity.ok(offers);
    }
}
