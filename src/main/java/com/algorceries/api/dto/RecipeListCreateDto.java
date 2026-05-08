package com.algorceries.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RecipeListCreateDto(
	@NotBlank
	@Size(max = 255) String name
) {
	// noop
}
