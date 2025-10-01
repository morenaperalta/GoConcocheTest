package com.more_than_code.go_con_coche.vehicle_rental_offer.controllers;

import com.more_than_code.go_con_coche.vehicle_rental_offer.dtos.search.SearchCriteria;
import com.more_than_code.go_con_coche.vehicle_rental_offer.dtos.search.SearchOfferRequest;
import com.more_than_code.go_con_coche.vehicle_rental_offer.dtos.search.SearchOfferResponse;
import com.more_than_code.go_con_coche.vehicle_rental_offer.services.RentalOfferSearchService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
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

    @GetMapping("/search/by-city")
    @Operation(summary = "Search offers by city")
    public ResponseEntity<List<SearchOfferResponse>> searchOffersByCriteria(
            @Parameter(description = "City name (case insensitive, partial match)")
            @RequestParam(required = false) String city,

            @Parameter(description = "Start date and time")
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDateTime,

            @Parameter(description = "End date and time")
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDateTime
    ) {
        SearchCriteria criteria = SearchCriteria.builder()
                .city(city)
                .startDateTime(startDateTime)
                .endDateTime(endDateTime)
                .build();

        List<SearchOfferResponse> offers = searchService.searchWithCriteria(criteria);
        return ResponseEntity.ok(offers);

    }
}
