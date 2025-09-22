package com.more_than_code.go_con_coche.role.services;

import com.more_than_code.go_con_coche.role.RoleRepository;
import com.more_than_code.go_con_coche.role.dtos.RoleMapperImpl;
import com.more_than_code.go_con_coche.role.dtos.RoleRequest;
import com.more_than_code.go_con_coche.role.dtos.RoleResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final RoleMapperImpl roleMapperImpl;

    public List<RoleResponse> getAllRoles() {
        return roleRepository.findAll()
                .stream()
                .map(role -> roleMapperImpl.entityToDto(role))
                .collect(Collectors.toList());
    }

}
