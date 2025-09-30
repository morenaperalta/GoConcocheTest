package com.more_than_code.go_con_coche.renter_profile;

import com.more_than_code.go_con_coche.registered_user.RegisteredUser;
import com.more_than_code.go_con_coche.renter_profile.models.RenterProfile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RenterProfileRepository extends JpaRepository<RenterProfile, Long> {
    boolean existsByRegisteredUser(RegisteredUser registeredUser);
    Optional<RenterProfile> findByRegisteredUserId(Long id);
    Optional<RenterProfile> findByRegisteredUserUsername(String username);
}
