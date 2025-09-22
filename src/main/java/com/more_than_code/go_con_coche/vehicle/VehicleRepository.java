package com.more_than_code.go_con_coche.vehicle;

import com.more_than_code.go_con_coche.vehicle.models.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Long> {
    boolean existsByVin(String vin);
    boolean existsByPlateNumber(String plateNumber);
    boolean existsByInsuranceNumber(String insuranceNumber);
}