package com.algorceries.api.security;

import java.io.IOException;
import java.util.function.Supplier;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.algorceries.api.repository.HouseholdRepository;

@Component
@Profile("dev")
public class DevAuthFilter extends OncePerRequestFilter {

    private final HouseholdRepository householdRepository;

    public DevAuthFilter(HouseholdRepository householdRepository) {
        this.householdRepository = householdRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
        throws ServletException, IOException {
        final String devHouseholdName = "dev";
        final Supplier<IllegalStateException> devHouseholdNotFoundExceptionSupplier = () -> {
            return new IllegalStateException("Household \"\" not found!".formatted(devHouseholdName));
        };

        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
            new JwtUserDetails(
                "dev",
                householdRepository.findByName(devHouseholdName)
                    .getOrElseThrow(devHouseholdNotFoundExceptionSupplier)
                    .getId()
            ), null, null
        );
        SecurityContextHolder.getContext().setAuthentication(auth);
        filterChain.doFilter(request, response);
    }
}
