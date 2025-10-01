package com.more_than_code.go_con_coche.vehicle_rental_offer;

import com.more_than_code.go_con_coche.location.Location;
import com.more_than_code.go_con_coche.vehicle.models.Vehicle;
import com.more_than_code.go_con_coche.vehicle_rental_offer.dtos.search.SearchCriteria;
import com.more_than_code.go_con_coche.vehicle_rental_offer.models.VehicleRentalOffer;
import jakarta.persistence.criteria.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class VehicleRentalofferSpecification {
    public static Specification<VehicleRentalOffer> withCriteria(SearchCriteria criteria) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            Join<VehicleRentalOffer, Vehicle> vehicleJoin = root.join("vehicle", JoinType.INNER);
            Join<VehicleRentalOffer, Location> locationJoin = root.join("location", JoinType.INNER);

            predicates.add(criteriaBuilder.isTrue(root.get("isAvailable")));

            if (criteria.getCity() != null && !criteria.getCity().trim().isEmpty()) {
                predicates.add(criteriaBuilder.like(
                        criteriaBuilder.lower(locationJoin.get("city")),
                        "%" + criteria.getCity().toLowerCase() + "%"
                ));
            }

            if (criteria.getStartDateTime() != null && criteria.getEndDateTime() != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(
                        root.get("startDateTime"),
                        criteria.getStartDateTime()
                ));
                predicates.add(criteriaBuilder.lessThanOrEqualTo(
                        root.get("endDateTime"),
                        criteria.getEndDateTime()
                ));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));

        };

    }
}
