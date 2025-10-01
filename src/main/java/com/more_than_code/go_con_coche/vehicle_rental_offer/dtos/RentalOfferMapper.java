package com.more_than_code.go_con_coche.vehicle_rental_offer.dtos;

import com.more_than_code.go_con_coche.location.dtos.LocationMapper;
import com.more_than_code.go_con_coche.vehicle.dtos.VehicleMapper;
import com.more_than_code.go_con_coche.vehicle_rental_offer.models.VehicleRentalOffer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RentalOfferMapper {
    private final VehicleMapper vehicleMapper;
    private final LocationMapper locationMapper;

    public VehicleRentalOffer toEntity(RentalOfferRequest request){
        return VehicleRentalOffer.builder()
                .startDateTime(request.startDateTime())
                .endDateTime(request.endDateTime())
                .priceHour(request.priceHour())
                .build();
    }

    public RentalOfferResponse toRentalOfferResponse(VehicleRentalOffer entity){
        return RentalOfferResponse.builder()
                .id(entity.getId())
                .vehicle(vehicleMapper.toVehicleOfferResponse(entity.getVehicle()))
                .location(locationMapper.toResponse(entity.getLocation()))
                .startDateTime(entity.getStartDateTime())
                .endDateTime(entity.getEndDateTime())
                .priceHour(entity.getPriceHour())
                .build();
    }
}