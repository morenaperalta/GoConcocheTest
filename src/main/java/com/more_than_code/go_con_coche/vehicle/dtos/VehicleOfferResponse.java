package com.more_than_code.go_con_coche.vehicle.dtos;

import com.more_than_code.go_con_coche.vehicle.models.FuelTypeCar;
import com.more_than_code.go_con_coche.vehicle.models.Seater;
import lombok.Builder;

@Builder
public record VehicleOfferResponse(Long id,
                                   String model,
                                   int year,
                                   String color,
                                   Seater seats,
                                   FuelTypeCar fuelTypeCar,
                                   String fuelConsumption,
                                   String imageUrl) {
}
