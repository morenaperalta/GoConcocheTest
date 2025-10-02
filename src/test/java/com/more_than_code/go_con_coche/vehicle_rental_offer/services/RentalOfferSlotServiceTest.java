package com.more_than_code.go_con_coche.vehicle_rental_offer.services;

import com.more_than_code.go_con_coche.vehicle_rental_offer.models.RentalOfferSlot;
import com.more_than_code.go_con_coche.vehicle_rental_offer.repositories.RentalOfferSlotRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RentalOfferSlotServiceTest {
    @Mock
    private RentalOfferSlotRepository slotRepository;

    @InjectMocks
    private RentalOfferSlotService slotService;

    private LocalDateTime from;
    private LocalDateTime to;

    @BeforeEach
    void setUp() {
        from = LocalDateTime.of(2025, 1, 10, 10, 0);
        to = LocalDateTime.of(2025, 1, 11, 10, 0);
    }

    @Test
    void getSlotsWithinPeriod_returnsSlotsFromRepository() {
        Long offerId = 1L;
        RentalOfferSlot slot1 = RentalOfferSlot.builder()
                .id(10L)
                .slotStart(from)
                .slotEnd(from.plusHours(8))
                .isAvailable(true)
                .build();

        RentalOfferSlot slot2 = RentalOfferSlot.builder()
                .id(11L)
                .slotStart(from.plusHours(8))
                .slotEnd(to)
                .isAvailable(true)
                .build();
        List<RentalOfferSlot> expectedSlots = List.of(slot1, slot2);
        when(slotRepository.findSlotsWithinPeriod(offerId, from, to)).thenReturn(expectedSlots);
        List<RentalOfferSlot> result = slotService.getSlotsWithinPeriod(offerId, from, to);
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(slot1, result.get(0));
        assertEquals(slot2, result.get(1));
    }

    @Test void getSlotsWithinPeriod_noSlotsFound_returnsEmptyList() {
        Long offerId = 2L;
        when(slotRepository.findSlotsWithinPeriod(offerId, from, to)).thenReturn(Collections.emptyList());
        List<RentalOfferSlot> result = slotService.getSlotsWithinPeriod(offerId, from, to);
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
}