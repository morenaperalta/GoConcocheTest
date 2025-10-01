package com.more_than_code.go_con_coche.registered_user;

import com.more_than_code.go_con_coche.role.Role;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "registered_users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegisteredUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column (name = "last_name", nullable = false)
    private String lastName;

    @Column (name = "date_of_birth", nullable = false)
    private LocalDate dateOfBirth;

    @Column (name = "phone_number", nullable = false, unique = true)
    private String phoneNumber;

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();
}
