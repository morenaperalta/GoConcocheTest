package com.more_than_code.go_con_coche.vehicle_rental_offer.dtos.search;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SearchCriteria {
    private String city;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
}
