package com.more_than_code.go_con_coche.auth;

import com.more_than_code.go_con_coche.auth.services.CustomUserDetailsService;
import com.more_than_code.go_con_coche.auth.services.JwtService;
import com.more_than_code.go_con_coche.registered_user.RegisteredUser;
import com.more_than_code.go_con_coche.role.Role;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import java.io.IOException;
import java.util.List;
import java.util.Set;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class JwtAuthenticationFilterTest {
    @Mock
    private JwtService jwtService;

    @Mock
    private CustomUserDetailsService userDetailsService;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private FilterChain filterChain;

    @InjectMocks
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @BeforeEach
    void setUp() {
        SecurityContextHolder.clearContext();
    }

    @Test
    void shouldPassWhenNoToken() throws ServletException, IOException {
        when(request.getServletPath()).thenReturn("/api/protected");
        when(request.getHeader("Authorization")).thenReturn(null);

        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

        verify(filterChain).doFilter(request, response);
        verifyNoInteractions(jwtService, userDetailsService);
    }

    @Test
    void shouldPassWhenAlreadyAuthenticated() throws Exception {
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(new UsernamePasswordAuthenticationToken("user", null, List.of()));
        SecurityContextHolder.setContext(context);

        when(request.getServletPath()).thenReturn("/api/protected");
        when(request.getHeader("Authorization")).thenReturn("Bearer sometoken");

        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

        verify(filterChain).doFilter(request, response);
        verifyNoInteractions(jwtService, userDetailsService);
    }

    @Test
    void shouldAuthenticateWhenValidAccessToken() throws ServletException, IOException {
        String token = "Bearer validtoken";
        String username = "testUser";

        when(request.getServletPath()).thenReturn("/api/protected");
        when(request.getHeader("Authorization")).thenReturn(token);
        when(jwtService.extractSubject("validtoken")).thenReturn(username);
        when(jwtService.validateToken("validtoken")).thenReturn(true);
        when(jwtService.extractTokenType("validtoken")).thenReturn("access_token");

        Role userRole = Role.builder().id(1L).role("USER").build();

        RegisteredUser domainUser = RegisteredUser.builder()
                .id(1L)
                .username(username)
                .password("password")
                .roles(Set.of(userRole))
                .build();

        SecurityUser securityUser = new SecurityUser(domainUser);

        when(userDetailsService.loadUserByUsername(username)).thenReturn(securityUser);

        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

        var auth = SecurityContextHolder.getContext().getAuthentication();

        verify(userDetailsService).loadUserByUsername(username);
        verify(filterChain).doFilter(request, response);

        assertThat(auth)
                .isInstanceOf(UsernamePasswordAuthenticationToken.class)
                .extracting(Authentication::getName)
                .isEqualTo(username);

        assertThat(auth.getAuthorities())
                .extracting("authority")
                .containsExactly("ROLE_USER");
    }

    @Test
    void shouldNotAuthenticateWhenInvalidToken() throws ServletException, IOException {
        String token = "Bearer invalidtoken";

        when(request.getServletPath()).thenReturn("/api/protected");
        when(request.getHeader("Authorization")).thenReturn(token);
        when(jwtService.extractSubject("invalidtoken")).thenReturn("testUser");
        when(jwtService.validateToken("invalidtoken")).thenReturn(false);

        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

        verify(filterChain).doFilter(request, response);
        assertThat(SecurityContextHolder.getContext().getAuthentication()).isNull();
    }
}