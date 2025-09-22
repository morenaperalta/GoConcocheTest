package com.more_than_code.go_con_coche.vehicle.dtos;

import com.more_than_code.go_con_coche.vehicle.models.Vehicle;
import org.springframework.stereotype.Component;

@Component
public class VehicleMapper {

    public Vehicle toEntity(VehicleRequest vehicleRequest) {
        return Vehicle.builder()
                .vin(vehicleRequest.vin())
                .plate_number(vehicleRequest.plate_number())
                .insurance_number(vehicleRequest.insurance_number())
                .model(vehicleRequest.model())
                .brand(vehicleRequest.brand())
                .year(vehicleRequest.year())
                .color(vehicleRequest.color())
                .seater(vehicleRequest.seater())
                .child_seats_number(vehicleRequest.child_seats_number())
                .fuel_type_car(vehicleRequest.fuel_type_car())
                .fuel_consumption(vehicleRequest.fuel_consumption())
                .image_url(vehicleRequest.image_url())
                .build();
    }

    public VehicleResponse toResponse(Vehicle vehicle) {
        return VehicleResponse.builder()
                .id(vehicle.getId())
                .vin(vehicle.getVin())
                .plate_number(vehicle.getPlate_number())
                .insurance_number(vehicle.getInsurance_number())
                .model(vehicle.getModel())
                .brand(vehicle.getBrand())
                .year(vehicle.getYear())
                .color(vehicle.getColor())
                .seater(vehicle.getSeater())
                .child_seats_number(vehicle.getChild_seats_number())
                .fuel_type_car(vehicle.getFuel_type_car())
                .fuel_consumption(vehicle.getFuel_consumption())
                .image_url(vehicle.getImage_url())
                .build();
    }
}