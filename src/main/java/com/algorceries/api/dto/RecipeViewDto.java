package com.algorceries.api.dto;

import java.util.List;

public record RecipeViewDto(
	String id,
	String name,
	List<String> tags,
	String householdId
) {
	// noop
}
