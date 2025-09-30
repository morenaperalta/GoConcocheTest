package com.more_than_code.go_con_coche.vehicle_reservation;

import com.more_than_code.go_con_coche.renter_profile.models.RenterProfile;
import com.more_than_code.go_con_coche.vehicle_rental_offer.models.VehicleRentalOffer;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "vehicle_reservations")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VehicleReservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column (name = "reservation_code", nullable = false, unique = true)
    private String reservationCode;

    @Column (name = "start_date_time", nullable = false)
    private LocalDateTime startDateTime;

    @Column (name = "end_date_time", nullable = false)
    private LocalDateTime endDateTime;

    @Column (name = "travellers_number", nullable = false)
    private Integer travellersNumber;

    @Column (name = "total_price", nullable = false)
    private double totalPrice;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private ReservationStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "renter_profile_id", nullable = false)
    private RenterProfile renter;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "offer_id", nullable = false)
    private VehicleRentalOffer rentalOffer;
}