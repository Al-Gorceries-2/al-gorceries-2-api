package com.algorceries.api.security;

import java.io.IOException;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import com.algorceries.api.entity.User;
import com.algorceries.api.service.IJwtService;
import com.algorceries.api.service.IUserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {

    private final IUserService userService;
    private final IJwtService jwtService;

    public OAuth2SuccessHandler(IUserService userService, IJwtService jwtService) {
        this.userService = userService;
        this.jwtService = jwtService;
    }

    @Override
    public void onAuthenticationSuccess(
        HttpServletRequest request, HttpServletResponse response, Authentication authentication
    ) throws IOException {
        OAuth2AuthenticationToken oauthToken = (OAuth2AuthenticationToken) authentication;
        String provider = oauthToken.getAuthorizedClientRegistrationId();

        OAuth2User oAuth2User = oauthToken.getPrincipal();
        String email = oAuth2User.getAttribute("email");

        // Check if user exists or create
        User user = userService.findByEmailAndProvider(email, provider)
            .orElseGet(() -> userService.save(new User(provider, email)));

        // Generate JWT
        String token = jwtService.generateToken(user);

        // Set JWT in an HttpOnly cookie
        Cookie cookie = new Cookie("jwt", token);
        cookie.setHttpOnly(true); // not accessible via JS
        cookie.setSecure(false); // true in production with HTTPS
        cookie.setPath("/"); // accessible to your whole domain
        cookie.setMaxAge(60 * 60); // 1 hour
        response.addCookie(cookie);

        response.sendRedirect("http://localhost:5173");
    }
}
