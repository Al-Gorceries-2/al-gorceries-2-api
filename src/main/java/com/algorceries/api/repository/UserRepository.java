package com.algorceries.api.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.algorceries.api.entity.User;
import io.vavr.control.Option;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByEmailAndProvider(String email, String provider);

    default Option<User> findByIdOption(String id) {
        return Option.ofOptional(findById(id));
    }

}
