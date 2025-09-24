package com.more_than_code.go_con_coche.vehicle.dtos;

import com.more_than_code.go_con_coche.vehicle.models.FuelTypeCar;
import com.more_than_code.go_con_coche.vehicle.models.Seater;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record VehicleRequest (
        @NotNull
        Long ownerId,
        @NotBlank
        String vin,
        @NotBlank
        String plateNumber,
        @NotBlank
        String insuranceNumber,
        @NotBlank
        String model,
        @NotBlank
        String brand,
        @NotNull
        Integer year,
        @NotBlank
        String color,
        @NotNull
        Seater seater,
        Integer childSeatsNumber,
        @NotNull
        FuelTypeCar fuelTypeCar,
        @NotBlank
        String fuelConsumption,
        @NotBlank
        String imageUrl
) {
}