package com.algorceries.api.service.impl;

import java.util.Optional;
import org.springframework.stereotype.Service;
import com.algorceries.api.entity.User;
import com.algorceries.api.repository.UserRepository;
import com.algorceries.api.service.IUserService;
import io.vavr.control.Option;
import jakarta.persistence.EntityNotFoundException;

@Service
public class UserService implements IUserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Option<User> findById(String id) {
        return userRepository.findByIdOption(id);
    }

    @Override
    public Optional<User> findByEmailAndProvider(String email, String provider) {
        return userRepository.findByEmailAndProvider(email, provider);
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public void changeUserDisplayName(String id, String newDisplayName) {
        User user = userRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        user.setDisplayName(newDisplayName);
        userRepository.save(user);
    }
}
