package com.more_than_code.go_con_coche.vehicle_rental_offer.repositories;

import com.more_than_code.go_con_coche.vehicle_rental_offer.models.RentalOfferSlot;
import com.more_than_code.go_con_coche.vehicle_rental_offer.models.VehicleRentalOffer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface VehicleRentalOfferRepository extends JpaRepository<VehicleRentalOffer, Long> {
    @Query("SELECT CASE WHEN COUNT(o) > 0 THEN true ELSE false END " +
            "FROM VehicleRentalOffer o " +
            "WHERE o.vehicle.id = :vehicleId " +
            "AND o.startDateTime < :endDateTime " +
            "AND o.endDateTime > :startDateTime")
    boolean existsOverlappingOffer(@Param("vehicleId") Long vehicleId,
                                   @Param("startDateTime") LocalDateTime startDateTime,
                                   @Param("endDateTime") LocalDateTime endDateTime);

    @Query("SELECT s FROM RentalOfferSlot s " +
            "WHERE s.offer.id = :offerId " +
            "AND s.slotStart >= :from " +
            "AND s.slotEnd <= :to")
    List<RentalOfferSlot> findSlotsWithinPeriod(@Param("offerId") Long offerId,
                                                @Param("from") LocalDateTime from,
                                                @Param("to") LocalDateTime to);
}
