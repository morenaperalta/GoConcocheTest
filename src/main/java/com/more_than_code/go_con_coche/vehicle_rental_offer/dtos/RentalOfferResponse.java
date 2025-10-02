package com.more_than_code.go_con_coche.vehicle_rental_offer.dtos;

import com.more_than_code.go_con_coche.location.dtos.LocationResponse;
import com.more_than_code.go_con_coche.vehicle.dtos.VehicleOfferResponse;
import lombok.Builder;
import java.time.LocalDateTime;

@Builder
public record RentalOfferResponse(Long id,
                                  VehicleOfferResponse vehicle,
                                  LocationResponse location,
                                  LocalDateTime startDateTime,
                                  LocalDateTime endDateTime,
                                  double priceHour) {
}