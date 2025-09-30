package com.more_than_code.go_con_coche.role;

import com.more_than_code.go_con_coche.role.dtos.RoleResponse;
import com.more_than_code.go_con_coche.role.services.RoleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/roles")
@PreAuthorize("hasRole('ADMIN')")
@Tag(name = "Role", description = "Operations related to roles from users.")
public class RoleController {

    private final RoleService roleService;

    @GetMapping
    @Operation(summary = "Get all roles", description = "Get list of all roles and returns the role details")
    public ResponseEntity<List<RoleResponse>> getAllRoles() {
        List<RoleResponse> roles = roleService.getAllRoles();
        return ResponseEntity.ok(roles);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get roles by id", description = "Get role by id and returns the role details")
    public ResponseEntity<RoleResponse> getRoleById(@PathVariable Long id) {
        RoleResponse role = roleService.getRoleById(id);
        return ResponseEntity.ok(role);
    }

}
