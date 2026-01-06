package com.algorceries.api.service;

import com.algorceries.api.entity.User;

public interface IJwtService {

    String generateToken(User user);

    String getSubject(String token);

    boolean isExpired(String token);
}
