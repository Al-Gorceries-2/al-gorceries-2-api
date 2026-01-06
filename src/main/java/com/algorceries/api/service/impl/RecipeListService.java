package com.algorceries.api.service.impl;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import com.algorceries.api.entity.Household;
import com.algorceries.api.entity.Recipe;
import com.algorceries.api.entity.RecipeList;
import com.algorceries.api.repository.RecipeListRepository;
import com.algorceries.api.service.IHouseholdService;
import com.algorceries.api.service.IRecipeListService;
import com.algorceries.api.service.IRecipeService;
import io.vavr.control.Try;
import jakarta.persistence.EntityNotFoundException;

@Service
public class RecipeListService implements IRecipeListService {

    private final RecipeListRepository repository;

    private final IRecipeService recipeService;
    private final IHouseholdService householdService;

    public RecipeListService(
        RecipeListRepository repository, IRecipeService recipeService, IHouseholdService householdService
    ) {
        this.repository = repository;
        this.recipeService = recipeService;
        this.householdService = householdService;
    }

    @Override
    public List<RecipeList> findAllByHouseholdId(String householdId) {
        return repository.findAllByHouseholdId(householdId);
    }

    @Override
    public Optional<RecipeList> findById(String id) {
        return repository.findById(id);
    }

    @Override
    public RecipeList save(RecipeList recipeList) {
        return repository.save(recipeList);
    }

    @Override
    public Try<RecipeList> save(RecipeList recipeList, String householdId) {
        Try<Household> household = householdService.findById(householdId)
            .toTry(() -> new EntityNotFoundException("Household with id %s not found".formatted(householdId)));

        return household.map(h -> {
            recipeList.setHousehold(h);
            return recipeList;
        }).map(repository::save);
    }

    @Override
    public Try<RecipeList> likeRecipe(String recipeListId, String recipeId) {
        Try<RecipeList> recipeList = repository.findByIdOption(recipeListId)
            .toTry(() -> new EntityNotFoundException("Recipe list with id %s not found".formatted(recipeListId)));

        Try<Recipe> recipe = recipeService.findById(recipeId)
            .toTry(() -> new EntityNotFoundException("Recipe with id %s not found".formatted(recipeId)));

        return recipeList.flatMap(rl -> recipe.map(r -> {
            rl.getLikedRecipes().add(r);
            rl.getDislikedRecipes().removeIf(dislikedRecipe -> dislikedRecipe.getId().equals(recipeId));
            return rl;
        })).map(repository::save);
    }

    @Override
    public Try<RecipeList> dislikeRecipe(String recipeListId, String recipeId) {
        Try<RecipeList> recipeList = repository.findByIdOption(recipeListId)
            .toTry(() -> new EntityNotFoundException("Recipe list with id %s not found".formatted(recipeListId)));

        Try<Recipe> recipe = recipeService.findById(recipeId)
            .toTry(() -> new EntityNotFoundException("Recipe with id %s not found".formatted(recipeId)));

        return recipeList.flatMap(rl -> recipe.map(r -> {
            rl.getDislikedRecipes().add(r);
            rl.getLikedRecipes().removeIf(dislikedRecipe -> dislikedRecipe.getId().equals(recipeId));
            return rl;
        })).map(repository::save);
    }
}
