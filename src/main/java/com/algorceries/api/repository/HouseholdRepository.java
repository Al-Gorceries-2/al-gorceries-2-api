package com.algorceries.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.algorceries.api.entity.Household;
import io.vavr.control.Option;

@Repository
public interface HouseholdRepository extends JpaRepository<Household, String> {
    default Option<Household> findByIdOption(String id) {
        return Option.ofOptional(findById(id));
    }
}
