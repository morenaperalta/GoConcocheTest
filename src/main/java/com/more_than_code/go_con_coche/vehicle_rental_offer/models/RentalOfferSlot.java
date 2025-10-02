package com.more_than_code.go_con_coche.vehicle_rental_offer.models;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "rental_offer_slots")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RentalOfferSlot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "offer_id", nullable = false)
    private VehicleRentalOffer offer;

    @Column(name = "slot_start",nullable = false)
    private LocalDateTime slotStart;

    @Column(name = "slot_end",nullable = false)
    private LocalDateTime slotEnd;

    @Column(name = "available",nullable = false)
    private boolean isAvailable;
}