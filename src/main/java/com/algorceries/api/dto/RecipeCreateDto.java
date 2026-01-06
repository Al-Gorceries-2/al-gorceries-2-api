package com.algorceries.api.dto;

import java.util.List;

public record RecipeCreateDto(
	String name,
	List<String> tags
) {
	// noop
}
