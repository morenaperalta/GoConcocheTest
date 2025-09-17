package com.more_than_code.go_con_coche.renter_profile;

import com.more_than_code.go_con_coche.registered_user.RegisteredUser;
import com.more_than_code.go_con_coche.role.Role;
import com.more_than_code.go_con_coche.vehicles.Seater;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "renter_profile")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RenterProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "registered_user_id", referencedColumnName = "id")
    private RegisteredUser registeredUserId;

    @Enumerated(EnumType.STRING)
    @Column (nullable = false)
    private TypeLicense typeLicense;

    @Column(name = "licence_number", nullable = false)
    private String licenceNumber;

    @CreationTimestamp
    @Column(name = "expired_date")
    public LocalDateTime expiredDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @NotBlank
    @Column(name = "image_url", nullable = false)
    private String imageURL;
}
