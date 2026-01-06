package com.algorceries.api.service;

import java.util.List;
import java.util.Optional;
import com.algorceries.api.entity.RecipeList;
import io.vavr.control.Try;

public interface IRecipeListService {

    List<RecipeList> findAllByHouseholdId(String householdId);

    Optional<RecipeList> findById(String id);

    RecipeList save(RecipeList recipeList);
    Try<RecipeList> save(RecipeList recipeList, String householdId);

    Try<RecipeList> likeRecipe(String recipeListId, String recipeId);
    Try<RecipeList> dislikeRecipe(String recipeListId, String recipeId);
}
