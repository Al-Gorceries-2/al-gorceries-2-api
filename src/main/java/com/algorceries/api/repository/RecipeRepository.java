package com.algorceries.api.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.algorceries.api.entity.Recipe;
import io.vavr.control.Option;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, String> {
    List<Recipe> findAllByHouseholdId(String householdId);

    default Option<Recipe> findByIdOption(String id) {
        return Option.ofOptional(findById(id));
    }

    @Modifying
    @Query(
        value = """
            DELETE FROM recipe_list_liked_recipes
            WHERE recipe_id = :recipeId
            """,
        nativeQuery = true
    )
    void deleteLikedRecipesByRecipeId(String recipeId);

    @Modifying
    @Query(
        value = """
            DELETE FROM recipe_list_disliked_recipes
            WHERE recipe_id = :recipeId
            """,
        nativeQuery = true
    )
    void deleteDislikedRecipesByRecipeId(String recipeId);
}
