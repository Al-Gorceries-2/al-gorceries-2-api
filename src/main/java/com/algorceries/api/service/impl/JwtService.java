package com.algorceries.api.service.impl;

import java.util.Date;
import javax.crypto.SecretKey;
import org.springframework.stereotype.Service;
import com.algorceries.api.entity.User;
import com.algorceries.api.service.IJwtService;
import io.jsonwebtoken.Jwts;

@Service
public class JwtService implements IJwtService {

    private static final SecretKey key = Jwts.SIG.HS512.key().build();

    @Override
    public String generateToken(User user) {
        return Jwts.builder()
            .subject(user.getId())
            .issuedAt(new Date(System.currentTimeMillis()))
            .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60))
            .signWith(key)
            .compact();
    }

    @Override
    public String getSubject(String token) {
        return Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload().getSubject();
    }

    @Override
    public boolean isExpired(String token) {
        return Jwts.parser()
            .verifyWith(key)
            .build()
            .parseSignedClaims(token)
            .getPayload()
            .getExpiration()
            .before(new Date());
    }
}
