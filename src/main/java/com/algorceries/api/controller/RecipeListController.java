package com.algorceries.api.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.algorceries.api.dto.RecipeListViewDto;
import com.algorceries.api.mapper.RecipeListMapper;
import com.algorceries.api.service.IRecipeListService;

@RestController
@RequestMapping
public class RecipeListController {

    private final IRecipeListService service;
    private final RecipeListMapper mapper;

    public RecipeListController(IRecipeListService service, RecipeListMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @PostMapping("/recipeList/{id}/like/{recipeId}")
    public ResponseEntity<RecipeListViewDto> likeRecipe(@PathVariable("id") String id, @PathVariable("recipeId") String recipeId) {
        return service.likeRecipe(id, recipeId)
            .fold(
                ex -> ResponseEntity.status(HttpStatus.NOT_FOUND).build(),
                recipeList -> ResponseEntity.ok(mapper.entityToViewDto(recipeList))
            );
    }

    @PostMapping("/recipeList/{id}/dislike/{recipeId}")
    public ResponseEntity<RecipeListViewDto> dislikeRecipe(@PathVariable("id") String id, @PathVariable("recipeId") String recipeId) {
        return service.dislikeRecipe(id, recipeId)
            .fold(
                ex -> ResponseEntity.status(HttpStatus.NOT_FOUND).build(),
                recipeList -> ResponseEntity.ok(mapper.entityToViewDto(recipeList))
            );
    }
}
