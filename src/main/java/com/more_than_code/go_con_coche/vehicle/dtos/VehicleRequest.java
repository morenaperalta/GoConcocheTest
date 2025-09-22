package com.more_than_code.go_con_coche.vehicle.dtos;

import com.more_than_code.go_con_coche.vehicle.models.FuelTypeCar;
import com.more_than_code.go_con_coche.vehicle.models.Seater;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record VehicleRequest (
        @NotBlank
        String vin,
        @NotBlank
        String plate_number,
        @NotBlank
        String insurance_number,
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
        Integer child_seats_number,
        @NotNull
        FuelTypeCar fuel_type_car,
        @NotBlank
        String fuel_consumption,
        @NotBlank
        String image_url
) {
}