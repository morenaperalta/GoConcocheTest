package com.more_than_code.go_con_coche.vehicle_rental_offer.dtos.search;

import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SearchCriteria {
    private String city;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private String model;
    private Integer seats;
}