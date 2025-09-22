package com.more_than_code.go_con_coche.role.services;

import com.more_than_code.go_con_coche.role.dtos.RoleRequest;
import com.more_than_code.go_con_coche.role.dtos.RoleResponse;

import java.util.List;

public interface RoleService {

    List<RoleResponse> getAllRoles ();

}
