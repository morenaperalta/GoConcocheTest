package com.more_than_code.go_con_coche.location;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class LocationTest {
    @Test
    void testBuilderAndGetters() {
        Location location = Location.builder()
                .id(1L)
                .city("Madrid")
                .address("Pl Tirso de Molino, 9, 28002")
                .build();

        assertThat(location.getId()).isEqualTo(1L);
        assertThat(location.getCity()).isEqualTo("Madrid");
        assertThat(location.getAddress()).isEqualTo("Pl Tirso de Molino, 9, 28002");
    }

    @Test
    void testSettersAndEquals() {
        Location loc1 = new Location();
        loc1.setId(1L);
        loc1.setCity("Barcelona");
        loc1.setDistrict("Eixample");
        loc1.setAddress("Av Catalunya, 22, 08025");

        Location loc2 = new Location(1L, "Barcelona", "Eixample", "Av Catalunya, 22, 08025");

        assertThat(loc1).isEqualTo(loc2);
        assertThat(loc1.hashCode()).isEqualTo(loc2.hashCode());
    }

    @Test
    void testToString() {
        Location location = Location.builder()
                .id(5L)
                .city("Valencia")
                .address("C/ Sevilla, 22, 46006")
                .build();

        String result = location.toString();

        assertThat(result).contains("Valencia");
        assertThat(result).contains("C/ Sevilla, 22, 46006");
    }
}