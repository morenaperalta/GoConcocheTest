package com.more_than_code.go_con_coche.vehicle_rental_offer;

import com.more_than_code.go_con_coche.location.Location;
import com.more_than_code.go_con_coche.vehicle.models.Seater;
import com.more_than_code.go_con_coche.vehicle.models.Vehicle;
import com.more_than_code.go_con_coche.vehicle_rental_offer.dtos.search.SearchCriteria;
import com.more_than_code.go_con_coche.vehicle_rental_offer.models.VehicleRentalOffer;
import jakarta.persistence.criteria.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class VehicleRentalOfferSpecification {
    public static Specification<VehicleRentalOffer> withCriteria(SearchCriteria criteria) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            query.distinct(true);

            Join<VehicleRentalOffer, Vehicle> vehicleJoin = root.join("vehicle", JoinType.INNER);
            Join<VehicleRentalOffer, Location> locationJoin = root.join("location", JoinType.INNER);

            predicates.add(criteriaBuilder.isTrue(root.get("isAvailable")));

            if (criteria.getCity() != null && !criteria.getCity().trim().isEmpty()) {
                predicates.add(criteriaBuilder.like(
                        criteriaBuilder.lower(locationJoin.get("city")),
                        "%" + criteria.getCity().toLowerCase() + "%"
                ));
            }

            if (criteria.getModel() != null && !criteria.getModel().trim().isEmpty()) {
                predicates.add(criteriaBuilder.like(
                        criteriaBuilder.lower(vehicleJoin.get("model")),
                        "%" + criteria.getModel().toLowerCase() + "%"
                ));
            }

            if(criteria.getSeats() != null) {
                final int requiredSeats = criteria.getSeats();

                List<Seater> matchingSeaters = Arrays.stream(Seater.values())
                        .filter(seater -> Seater.getSeatCount(seater) >= requiredSeats)
                        .collect(Collectors.toList());

                if (!matchingSeaters.isEmpty()) {
                    Predicate[] seaterPredicates = matchingSeaters.stream()
                            .map(seater -> criteriaBuilder.equal(vehicleJoin.get("seater"), seater))
                            .toArray(Predicate[]::new);

                    predicates.add(criteriaBuilder.or(seaterPredicates));
                } else {
                    return criteriaBuilder.disjunction();
                }
            }

            if (criteria.getStartDateTime() != null && criteria.getEndDateTime() != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(
                        root.get("startDateTime"),
                        criteria.getEndDateTime()
                ));
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(
                        root.get("endDateTime"),
                        criteria.getStartDateTime()
                ));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}