package com.algorceries.api.service;

import java.util.Optional;
import com.algorceries.api.entity.User;
import io.vavr.control.Option;

public interface IUserService {

    Option<User> findById(String id);

    Optional<User> findByEmailAndProvider(String email, String provider);

    User save(User user);

    void changeUserDisplayName(String id, String newDisplayName);
}
