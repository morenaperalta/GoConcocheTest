package com.more_than_code.go_con_coche.role.dtos;

import com.more_than_code.go_con_coche.role.Role;

public interface RoleMapper {
    Role dtoToEntity(RoleRequest request);
    RoleResponse entityToDto(Role role);
}
