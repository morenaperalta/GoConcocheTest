package com.more_than_code.go_con_coche.vehicle.models;

import lombok.*;

@Getter
@RequiredArgsConstructor
public enum Seater {
    SMART(2, "2 seats, compact car"),
    SEDAN(5, "5 seats, standard car"),
    SUV(5, "5-7 seats, sport utility vehicle"),
    VAN(7, "7+ seats, large capacity vehicle");

    private final int seatCount;
    private final String description;

    public static int getSeatCount(Seater seater) {
        return seater.seatCount;
    }
}