package com.more_than_code.go_con_coche.vehicle_rental_offer.repositories;

import com.more_than_code.go_con_coche.vehicle_rental_offer.models.RentalOfferSlot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface RentalOfferSlotRepository extends JpaRepository<RentalOfferSlot, Long> {

    @Query("SELECT s FROM RentalOfferSlot s " +
            "WHERE s.offer.id = :offerId " +
            "AND s.slotStart >= :from " +
            "AND s.slotEnd <= :to")
    List<RentalOfferSlot> findSlotsWithinPeriod(@Param("offerId") Long offerId,
                                                @Param("from") LocalDateTime from,
                                                @Param("to") LocalDateTime to);
}
