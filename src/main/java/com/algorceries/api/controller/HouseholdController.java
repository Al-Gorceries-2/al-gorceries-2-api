package com.algorceries.api.controller;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import com.algorceries.api.dto.HouseholdCreateDto;
import com.algorceries.api.dto.HouseholdViewDto;
import com.algorceries.api.dto.RecipeCreateDto;
import com.algorceries.api.dto.RecipeListCreateDto;
import com.algorceries.api.dto.RecipeListPatchDto;
import com.algorceries.api.dto.RecipeListViewDto;
import com.algorceries.api.dto.RecipeViewDto;
import com.algorceries.api.mapper.HouseholdMapper;
import com.algorceries.api.mapper.RecipeListMapper;
import com.algorceries.api.mapper.RecipeMapper;
import com.algorceries.api.security.JwtUserDetails;
import com.algorceries.api.service.IHouseholdService;
import com.algorceries.api.service.IRecipeListService;
import com.algorceries.api.service.IRecipeService;

@RestController
@RequestMapping("/households")
public class HouseholdController {

    private final IHouseholdService householdService;
    private final IRecipeService recipeService;
    private final IRecipeListService recipeListService;

    private final HouseholdMapper householdMapper;
    private final RecipeMapper recipeMapper;
    private final RecipeListMapper recipeListMapper;

    public HouseholdController(
        IHouseholdService householdService, IRecipeService recipeService, IRecipeListService recipeListService,
        HouseholdMapper householdMapper, RecipeMapper recipeMapper, RecipeListMapper recipeListMapper
    ) {
        this.householdService = householdService;
        this.recipeService = recipeService;
        this.recipeListService = recipeListService;
        this.householdMapper = householdMapper;
        this.recipeMapper = recipeMapper;
        this.recipeListMapper = recipeListMapper;
    }

    @GetMapping("/{id}")
    public ResponseEntity<HouseholdViewDto> getHouseholdById(
        @PathVariable("id") String id, @AuthenticationPrincipal JwtUserDetails user
    ) {
        if (!id.equals(user.getHouseholdId())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        return householdService.findById(id)
            .map(household -> ResponseEntity.ok(householdMapper.entityToViewDto(household)))
            .getOrElse(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PostMapping
    public ResponseEntity<HouseholdViewDto> createHousehold(
        @RequestBody HouseholdCreateDto householdCreateDto, @AuthenticationPrincipal JwtUserDetails user
    ) {
        if (user.getHouseholdId() != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        var household =
            householdService.create(householdMapper.createDtoToEntity(householdCreateDto), user.getUsername());
        return ResponseEntity
            .created(
                ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(household.getId()).toUri()
            )
            .body(householdMapper.entityToViewDto(household));
    }

    @PostMapping("/{id}/users")
    public ResponseEntity<HouseholdViewDto> addUser(
        @AuthenticationPrincipal UserDetails user, @PathVariable("id") String id
    ) {
        return householdService.addUser(id, user.getUsername())
            .fold(
                ex -> ResponseEntity.status(HttpStatus.NOT_FOUND).build(),
                household -> ResponseEntity.ok(householdMapper.entityToViewDto(household))
            );
    }

    @DeleteMapping("/{id}/users")
    public ResponseEntity<HouseholdViewDto> removeUser(
        @AuthenticationPrincipal UserDetails user, @PathVariable("id") String id
    ) {
        return householdService.removeUser(id, user.getUsername())
            .fold(
                ex -> ResponseEntity.status(HttpStatus.NOT_FOUND).build(),
                household -> ResponseEntity.noContent().build()
            );
    }

    @GetMapping("/{id}/recipes")
    public ResponseEntity<List<RecipeViewDto>> getRecipes(
        @PathVariable("id") String id, @AuthenticationPrincipal JwtUserDetails user
    ) {
        if (!id.equals(user.getHouseholdId())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        return ResponseEntity
            .ok(recipeService.findAllByHouseholdId(id).stream().map(recipeMapper::entityToViewDto).toList());
    }

    @PostMapping("{id}/recipes")
    public ResponseEntity<RecipeViewDto> createRecipe(
        @RequestBody RecipeCreateDto recipeCreateDto, @PathVariable("id") String id,
        @AuthenticationPrincipal JwtUserDetails user
    ) {
        if (!id.equals(user.getHouseholdId())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        return recipeService.save(recipeMapper.createDtoToEntity(recipeCreateDto), id)
            .fold(
                ex -> ResponseEntity.status(HttpStatus.NOT_FOUND).build(),
                recipe -> ResponseEntity.status(HttpStatus.CREATED).body(recipeMapper.entityToViewDto(recipe))
            );
    }

    @DeleteMapping("{id}/recipes/{recipeId}")
    public ResponseEntity<Void> deleteRecipe(
        @PathVariable("id") String id, @PathVariable("recipeId") String recipeId,
        @AuthenticationPrincipal JwtUserDetails user
    ) {
        if (!id.equals(user.getHouseholdId())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        recipeService.delete(recipeId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/recipeLists")
    public ResponseEntity<List<RecipeListViewDto>> getRecipeLists(@PathVariable("id") String id) {
        return ResponseEntity
            .ok(recipeListService.findAllByHouseholdId(id).stream().map(recipeListMapper::entityToViewDto).toList());
    }

    @PostMapping("/{id}/recipeLists")
    public ResponseEntity<RecipeListViewDto> createRecipeList(
        @RequestBody RecipeListCreateDto recipeListCreateDto, @PathVariable("id") String id,
        @AuthenticationPrincipal JwtUserDetails user
    ) {
        if (!id.equals(user.getHouseholdId())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        return recipeListService.save(recipeListMapper.createDtoToEntity(recipeListCreateDto), id)
            .fold(
                ex -> ResponseEntity.status(HttpStatus.NOT_FOUND).build(),
                recipeList -> ResponseEntity.status(HttpStatus.CREATED)
                    .body(recipeListMapper.entityToViewDto(recipeList))
            );
    }

    @PatchMapping("/{id}/recipeList/{recipeListId}")
    public ResponseEntity<RecipeListViewDto> updateRecipeList(
        @PathVariable("id") String id, @PathVariable("recipeListId") String recipeListId,
        @RequestBody RecipeListPatchDto recipeListPatchDto, @AuthenticationPrincipal JwtUserDetails user
    ) {
        if (!id.equals(user.getHouseholdId())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        return recipeListService.findById(recipeListId).map(recipeList -> {
            if (recipeListPatchDto.name() != null) {
                recipeList.setName(recipeListPatchDto.name());
            }

            return recipeList;
        })
            .map(recipeListService::save)
            .map(recipeListMapper::entityToViewDto)
            .map(ResponseEntity::ok)
            .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }
}
