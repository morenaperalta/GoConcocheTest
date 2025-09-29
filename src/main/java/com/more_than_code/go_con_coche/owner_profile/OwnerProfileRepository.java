package com.more_than_code.go_con_coche.owner_profile;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OwnerProfileRepository extends JpaRepository<OwnerProfile, Long> {
    Optional<OwnerProfile> findByRegisteredUserId(Long registeredUserId);
}