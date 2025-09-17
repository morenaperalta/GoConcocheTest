package com.more_than_code.go_con_coche.auth.dtos;
import com.more_than_code.go_con_coche.Role.Role;
import java.util.Set;

public record AuthResponse(Long id,
                           String username,
                           String token,
                           Set<Role> roles) {
}
