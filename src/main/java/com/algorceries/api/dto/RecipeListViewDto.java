package com.algorceries.api.dto;

import java.util.List;

public record RecipeListViewDto(
	String id,
	String name,
	List<RecipeViewDto> likedRecipes,
	List<RecipeViewDto> dislikedRecipes
) {
	// noop
}
