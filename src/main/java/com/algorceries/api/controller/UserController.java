package com.algorceries.api.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.algorceries.api.dto.MeViewDto;
import com.algorceries.api.security.JwtUserDetails;

@RestController
@RequestMapping
public class UserController {

	@GetMapping("/me")
	public ResponseEntity<MeViewDto> getMe(@AuthenticationPrincipal JwtUserDetails user) {
		return ResponseEntity.ok(new MeViewDto(user.getUsername(), user.getHouseholdId()));
	}
}
