package com.more_than_code.go_con_coche.vehicle.services;

import com.more_than_code.go_con_coche.vehicle.dtos.VehicleRequest;
import com.more_than_code.go_con_coche.vehicle.dtos.VehicleResponse;

public interface VehicleService {

    VehicleResponse createVehicle(VehicleRequest vehicleRequest);
}