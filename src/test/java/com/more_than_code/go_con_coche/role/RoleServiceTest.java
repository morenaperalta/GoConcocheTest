package com.more_than_code.go_con_coche.role;

import com.more_than_code.go_con_coche.role.dtos.RoleMapperImpl;
import com.more_than_code.go_con_coche.role.dtos.RoleResponse;
import com.more_than_code.go_con_coche.role.services.RoleService;
import com.more_than_code.go_con_coche.role.services.RoleServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
@DisplayName("RoleService Unit Test")
public class RoleServiceTest {

    @Mock
    RoleRepository roleRepository;

    @Mock
    RoleMapperImpl roleMapperImpl;

    @InjectMocks
    RoleServiceImpl roleServiceImpl;

    Role role;
    RoleResponse roleResponse;

    @BeforeEach
    void setUp() {
        role = new Role(1L, "ROLE_ADMIN", null);
        roleResponse = new RoleResponse(1L, "ROLE_ADMIN");
    }

    @Nested
    @DisplayName("getAllRoles")
    class GetAllRoles {

        @Test
        @DisplayName("getAllRoles")
        void shouldReturnAllRoles() {
            given(roleRepository.findAll()).willReturn(List.of(role));
            given(roleMapperImpl.entityToDto(role)).willReturn(roleResponse);

            List<RoleResponse> result = roleServiceImpl.getAllRoles();

            assertThat(result).isNotNull();
            assertThat(result).hasSize(1);
            assertThat(result.get(0).roleName()).isEqualTo("ROLE_ADMIN");
        }

    }


}
