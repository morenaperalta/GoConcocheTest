package com.more_than_code.go_con_coche.vehicle.services;

import com.more_than_code.go_con_coche.global.EntityAlreadyExistsException;
import com.more_than_code.go_con_coche.vehicle.VehicleRepository;
import com.more_than_code.go_con_coche.vehicle.dtos.VehicleMapper;
import com.more_than_code.go_con_coche.vehicle.dtos.VehicleRequest;
import com.more_than_code.go_con_coche.vehicle.dtos.VehicleResponse;
import com.more_than_code.go_con_coche.vehicle.models.Vehicle;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VehicleServiceImpl implements VehicleService{

    private final VehicleRepository vehicleRepository;
    private final VehicleMapper vehicleMapper;

    @Override
    @Transactional
    public VehicleResponse createVehicle(VehicleRequest vehicleRequest) {
        if (vehicleRepository.existsByVin(vehicleRequest.vin())) {
            throw new EntityAlreadyExistsException("Vehicle", "VIN", vehicleRequest.vin());
        }
        if (vehicleRepository.existsByPlateNumber(vehicleRequest.plateNumber())) {
            throw new EntityAlreadyExistsException("Vehicle", "plate number", vehicleRequest.plateNumber());
        }
        if (vehicleRepository.existsByInsuranceNumber(vehicleRequest.insuranceNumber())) {
            throw new EntityAlreadyExistsException("Vehicle", "insurance number", vehicleRequest.insuranceNumber());
        }

        Vehicle vehicle = vehicleMapper.toEntity(vehicleRequest);

        Vehicle savedVehicle = vehicleRepository.save(vehicle);

        return vehicleMapper.toResponse(savedVehicle);
    }
}