package com.enigmacamp.tokonyadia.security;

import com.enigmacamp.tokonyadia.model.dto.response.JwtClaims;
import com.enigmacamp.tokonyadia.model.entity.UserAccount;
import com.enigmacamp.tokonyadia.service.JwtService;
import com.enigmacamp.tokonyadia.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@Slf4j
public class AuthenticationFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final UserService userService;
    final String AUTH_HEADER = "Authorization";


    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        try {
            String bearerToken = request.getHeader(AUTH_HEADER);

            if (bearerToken != null && jwtService.verifyJwtToken(bearerToken)) {
                JwtClaims jwtClaims = jwtService.getClaimsByToken(bearerToken);
                UserAccount userAccount = userService.loadUserById(jwtClaims.getUserAccountId());
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        userAccount.getUsername(),
                        null,
                        userAccount.getAuthorities()
                );
                authentication.setDetails(new WebAuthenticationDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (Exception e) {
            log.error("Cannot set user authentication: {}", e.getMessage());
        }

        filterChain.doFilter(request, response);
    }
}
