package com.algorceries.api.security;

import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import com.algorceries.api.service.IJwtService;
import jakarta.annotation.Nonnull;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtCookieAuthFilter extends OncePerRequestFilter {

    private final IJwtService jwtService;
    private final UserDetailsService userDetailsService;

    public JwtCookieAuthFilter(IJwtService jwtService, UserDetailsService userDetailsService) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
        throws ServletException, IOException {

        var maybeToken = extractJwtFromCookies(request);

        if (maybeToken.isEmpty()) {
            // if (request.getRequestURI().startsWith("/api")) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            // } else {
            //     filterChain.doFilter(request, response);
            //     return;
            // }
        }

        String token = maybeToken.get();
        String username = jwtService.getSubject(token);
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        if (!jwtService.isExpired(token)) {
            UsernamePasswordAuthenticationToken authToken =
                new UsernamePasswordAuthenticationToken(userDetails, null, null);

            authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            SecurityContextHolder.getContext().setAuthentication(authToken);
        }

        filterChain.doFilter(request, response);
    }

    private Optional<String> extractJwtFromCookies(@Nonnull HttpServletRequest request) {
        return Optional.ofNullable(request.getCookies())
            .flatMap(cookies -> Arrays.stream(cookies).filter(cookie -> cookie.getName().equals("jwt")).findFirst())
            .map(Cookie::getValue);
    }
}
