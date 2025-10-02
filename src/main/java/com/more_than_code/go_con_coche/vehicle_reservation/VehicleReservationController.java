package com.more_than_code.go_con_coche.vehicle_reservation;

import com.more_than_code.go_con_coche.vehicle_reservation.dtos.VehicleReservationRequest;
import com.more_than_code.go_con_coche.vehicle_reservation.dtos.VehicleReservationResponse;
import com.more_than_code.go_con_coche.vehicle_reservation.services.VehicleReservationService;
import jakarta.validation.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/vehicle-reservations")
@RequiredArgsConstructor
public class VehicleReservationController {

    private final VehicleReservationService reservationService;

    @PostMapping
    @PreAuthorize("hasRole('RENTER')")
    public ResponseEntity<VehicleReservationResponse> createReservation(@Valid @RequestBody VehicleReservationRequest request) {
        VehicleReservationResponse response = reservationService.createReservation(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}