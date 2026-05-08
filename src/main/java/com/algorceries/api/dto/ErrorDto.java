package com.algorceries.api.dto;

import java.util.List;

public record ErrorDto(
	String key,
	List<String> messages
) {
	// noop
}
