package com.more_than_code.go_con_coche.UserRole;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "user_roles")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class UserRole {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    @Column (nullable = false, name = "role")
    private String role;

    //@ManyToMany(mappedBy = "user_roles")
    //private List<RegisteredUser> registeredUsers = new ArrayList<>();
}
