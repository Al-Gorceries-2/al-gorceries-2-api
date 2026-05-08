package com.algorceries.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RecipeListPatchDto(
	@NotBlank
	@Size(max = 255) String name
) {
	// noop
}
