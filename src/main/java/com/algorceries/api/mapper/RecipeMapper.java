package com.algorceries.api.mapper;

import org.mapstruct.Mapper;
import com.algorceries.api.dto.RecipeCreateDto;
import com.algorceries.api.dto.RecipeViewDto;
import com.algorceries.api.entity.Recipe;

@Mapper(componentModel = "spring")
public interface RecipeMapper {

    RecipeViewDto entityToViewDto(Recipe recipe);
    Recipe createDtoToEntity(RecipeCreateDto recipeCreateDto);
}
