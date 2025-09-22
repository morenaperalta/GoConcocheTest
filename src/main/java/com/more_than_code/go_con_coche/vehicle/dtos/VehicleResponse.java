package com.more_than_code.go_con_coche.vehicle.dtos;

import com.more_than_code.go_con_coche.vehicle.models.FuelTypeCar;
import com.more_than_code.go_con_coche.vehicle.models.Seater;
import lombok.Builder;

@Builder
public record VehicleResponse (
        Long id,
        String vin,
        String plate_number,
        String insurance_number,
        String model,
        String brand,
        Integer year,
        String color,
        Seater seater,
        Integer child_seats_number,
        FuelTypeCar fuel_type_car,
        String fuel_consumption,
        String image_url
){
}