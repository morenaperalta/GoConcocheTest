package com.more_than_code.go_con_coche.vehicle.models;

import com.more_than_code.go_con_coche.owner_profile.OwnerProfile;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.HashSet;

@Entity
@Table(name = "vehicles")
@Data
@AllArgsConstructor @NoArgsConstructor
@Builder
public class Vehicle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "vin", nullable = false, unique = true)
    private String vin;

    @Column(name = "plate_number", nullable = false, unique = true)
    private String plateNumber;

    @Column (name = "insurance_number", nullable = false, unique = true)
    private String insuranceNumber;

    @Column (name = "model", nullable = false)
    private String model;

    @Column (name = "brand", nullable = false)
    private String brand;

    @Column (name = "year", nullable = false)
    private Integer year;

    @Column (name = "color", nullable = false)
    private String color;

    @Enumerated(EnumType.STRING)
    @Column (name = "seater", nullable = false)
    private Seater seater;

    @Column (name = "child_seats_number")
    private Integer childSeatsNumber;

    @Enumerated(EnumType.STRING)
    @Column (name = "fuel_type_car", nullable = false)
    private FuelTypeCar fuelTypeCar;

    @Column (name = "fuel_consumption", nullable = false)
    private String fuelConsumption;

    @Column (name = "image_url", nullable = false)
    private String imageUrl;

    @Column (name = "public_image_id")
    private String publicImageId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private OwnerProfile owner;
}