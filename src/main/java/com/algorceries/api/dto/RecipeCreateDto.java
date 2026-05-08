package com.algorceries.api.dto;

import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record RecipeCreateDto(
	@NotBlank
	@Size(max = 255) String name,
	@NotNull List<String> tags
) {
	// noop
}
