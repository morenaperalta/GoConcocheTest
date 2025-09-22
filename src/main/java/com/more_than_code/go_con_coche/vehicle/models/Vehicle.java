package com.more_than_code.go_con_coche.vehicle.models;

import jakarta.persistence.*;
import lombok.*;

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

    @Column(nullable = false, unique = true)
    private String vin;

    @Column(nullable = false, unique = true)
    private String plate_number;

    @Column (nullable = false, unique = true)
    private String insurance_number;

    @Column (nullable = false)
    private String model;

    @Column (nullable = false)
    private String brand;

    @Column (nullable = false)
    private Integer year;

    @Column (nullable = false)
    private String color;

    @Enumerated(EnumType.STRING)
    @Column (nullable = false)
    private Seater seater;

    @Column
    private Integer child_seats_number;

    @Enumerated(EnumType.STRING)
    @Column (nullable = false)
    private FuelTypeCar fuel_type_car;

    @Column (nullable = false)
    private String fuel_consumption;

    @Column (nullable = false)
    private String image_url;
}