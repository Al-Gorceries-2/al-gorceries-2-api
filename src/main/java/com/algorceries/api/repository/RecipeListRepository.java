package com.algorceries.api.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.algorceries.api.entity.RecipeList;
import io.vavr.control.Option;

@Repository
public interface RecipeListRepository extends JpaRepository<RecipeList, String> {
    List<RecipeList> findAllByHouseholdId(String householdId);

    default Option<RecipeList> findByIdOption(String id) {
        return Option.ofOptional(findById(id));
    }
}
