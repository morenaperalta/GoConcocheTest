package com.more_than_code.go_con_coche.auth;

import com.more_than_code.go_con_coche.auth.services.CustomUserDetailsService;
import com.more_than_code.go_con_coche.auth.services.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final CustomUserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String path = request.getServletPath();

        if (path.startsWith("/api/auth") ||
                path.startsWith("/swagger") ||
                path.startsWith("/v3/api-docs") ||
                path.startsWith("/swagger-ui") ||
                path.startsWith("/health") ||
                path.startsWith("/actuator")) {

            filterChain.doFilter(request, response);
            return;
        }


        SecurityContext context = SecurityContextHolder.getContext();
        String jwtToken = request.getHeader("Authorization");

        if (jwtToken == null || context.getAuthentication() != null) {
            filterChain.doFilter(request, response);
            return;
        }

        jwtToken = jwtToken.substring(7);
        String username = jwtService.extractSubject(jwtToken);
        if (username != null && jwtService.validateToken(jwtToken) && jwtService.extractTokenType(jwtToken).equals("access_token")) {
            SecurityUser securityUser = (SecurityUser) userDetailsService.loadUserByUsername(username);
            var authToken = new UsernamePasswordAuthenticationToken(
                    securityUser,
                    null,
                    securityUser.getAuthorities()
            );
            authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            context.setAuthentication(authToken);
        }
        filterChain.doFilter(request, response);
    }
}
