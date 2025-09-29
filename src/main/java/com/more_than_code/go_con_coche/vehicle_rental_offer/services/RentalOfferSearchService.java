package com.more_than_code.go_con_coche.vehicle_rental_offer.services;
import com.more_than_code.go_con_coche.vehicle_rental_offer.dtos.search.SearchOfferResponse;
import com.more_than_code.go_con_coche.vehicle_rental_offer.dtos.search.SlotInfo;
import com.more_than_code.go_con_coche.vehicle_rental_offer.models.RentalOfferSlot;
import com.more_than_code.go_con_coche.vehicle_rental_offer.models.VehicleRentalOffer;
import com.more_than_code.go_con_coche.vehicle_rental_offer.repositories.VehicleRentalOfferRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RentalOfferSearchService {
    private final VehicleRentalOfferRepository offerRepository;
    private final RentalOfferSlotService slotService;

    public List<SearchOfferResponse> searchAvailableOffers(Long locationId, LocalDateTime from, LocalDateTime to) {
        List<VehicleRentalOffer> offers = offerRepository.findByLocationIdAndIsAvailableTrue(locationId);

        List<SearchOfferResponse> result = new ArrayList<>();
        for (VehicleRentalOffer offer : offers) {
            List<RentalOfferSlot> availableSlots = slotService.getSlotsWithinPeriod(offer.getId(), from, to)
                    .stream()
                    .filter(RentalOfferSlot::isAvailable)
                    .toList();

            if (!availableSlots.isEmpty()) {
                List<SlotInfo> slotsInfo = availableSlots.stream()
                        .map(s -> new SlotInfo(s.getSlotStart(), s.getSlotEnd()))
                        .toList();

                result.add(new SearchOfferResponse(
                        offer.getId(),
                        offer.getVehicle().getId(),
                        offer.getVehicle().getModel(),
                        offer.getVehicle().getBrand(),
                        offer.getLocation().getId(),
                        offer.getStartDateTime(),
                        offer.getEndDateTime(),
                        offer.getPriceHour(),
                        slotsInfo
                ));
            }
        }
        return result;
    }
}
