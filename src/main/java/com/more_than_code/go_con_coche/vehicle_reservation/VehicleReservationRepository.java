package com.more_than_code.go_con_coche.vehicle_reservation;

import com.more_than_code.go_con_coche.vehicle_reservation.models.VehicleReservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VehicleReservationRepository extends JpaRepository<VehicleReservation, Long> {
}