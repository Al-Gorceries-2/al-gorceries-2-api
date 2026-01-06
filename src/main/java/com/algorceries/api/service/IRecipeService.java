package com.algorceries.api.service;

import java.util.List;
import com.algorceries.api.entity.Recipe;
import io.vavr.control.Option;
import io.vavr.control.Try;

public interface IRecipeService {

    List<Recipe> findAllByHouseholdId(String householdId);

    Option<Recipe> findById(String id);

    Recipe save(Recipe recipe);
    Try<Recipe> save(Recipe recipe, String householdId);

    void delete(String id);
}
