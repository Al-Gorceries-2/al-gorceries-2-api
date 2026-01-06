package com.algorceries.api.service.impl;

import java.util.List;
import org.springframework.stereotype.Service;
import com.algorceries.api.entity.Household;
import com.algorceries.api.entity.Recipe;
import com.algorceries.api.repository.RecipeRepository;
import com.algorceries.api.service.IHouseholdService;
import com.algorceries.api.service.IRecipeService;
import io.vavr.control.Option;
import io.vavr.control.Try;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class RecipeService implements IRecipeService {

    private final RecipeRepository repository;

    private final IHouseholdService householdService;

    public RecipeService(RecipeRepository repository, IHouseholdService householdService) {
        this.repository = repository;
        this.householdService = householdService;
    }

    @Override
    public List<Recipe> findAllByHouseholdId(String householdId) {
        return repository.findAllByHouseholdId(householdId);
    }

    @Override
    public Option<Recipe> findById(String id) {
        return repository.findByIdOption(id);
    }

    @Override
    public Recipe save(Recipe recipe) {
        return repository.save(recipe);
    }

    @Override
    public Try<Recipe> save(Recipe recipe, String householdId) {
        Try<Household> household = householdService.findById(householdId)
            .toTry(() -> new EntityNotFoundException("Household with id %s not found".formatted(householdId)));

        return household.map(h -> {
            recipe.setHousehold(h);
            return repository.save(recipe);
        });
    }

    @Override
    @Transactional
    public void delete(String id) {
        repository.deleteLikedRecipesByRecipeId(id);
        repository.deleteDislikedRecipesByRecipeId(id);
        repository.deleteById(id);
    }
}
