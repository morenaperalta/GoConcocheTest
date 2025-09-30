package com.more_than_code.go_con_coche.vehicle_reservation.dtos;

import com.more_than_code.go_con_coche.vehicle.dtos.VehicleMapper;
import com.more_than_code.go_con_coche.vehicle_reservation.models.VehicleReservation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class VehicleReservationMapper {

    private final VehicleMapper vehicleMapper;

    public VehicleReservationResponse toResponse(VehicleReservation reservation) {
        return VehicleReservationResponse.builder()
                .id(reservation.getId())
                .reservationCode(reservation.getReservationCode())
                .renterProfileId(reservation.getRenter().getId())
                .rentalOfferId(reservation.getRentalOffer().getId())
                .vehicleDetails(vehicleMapper.toVehicleOfferResponse(reservation.getRentalOffer().getVehicle()))
                .startDateTime(reservation.getStartDateTime())
                .endDateTime(reservation.getEndDateTime())
                .totalPrice(reservation.getTotalPrice())
                .travellersNumber(reservation.getTravellersNumber())
                .status(reservation.getStatus())
                .build();
    }
}