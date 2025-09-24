package com.more_than_code.go_con_coche.role.dtos;

import com.more_than_code.go_con_coche.role.Role;
import org.springframework.stereotype.Component;

@Component
public class RoleMapperImpl implements RoleMapper {

    @Override
    public Role dtoToEntity(RoleRequest request) {
        if (request == null) return null;

        return Role.builder()
                .role(request.roleName())
                .build();
    }

    @Override
    public RoleResponse entityToDto(Role role) {
        if (role == null) return null;

        return new RoleResponse(
                role.getId(),
                role.getRole()
        );
    }


}
