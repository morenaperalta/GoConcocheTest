package com.more_than_code.go_con_coche.role;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.more_than_code.go_con_coche.registered_user.RegisteredUser;
import jakarta.persistence.*;
import lombok.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "roles")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class Role {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    @Column (nullable = false, name = "role")
    private String role;

    @JsonIgnore
    @ManyToMany(mappedBy = "roles")
    private List<RegisteredUser> registeredUsers = new ArrayList<>();
}
