package com.algorceries.api.mapper;

import org.mapstruct.Mapper;
import com.algorceries.api.dto.RecipeListCreateDto;
import com.algorceries.api.dto.RecipeListViewDto;
import com.algorceries.api.entity.RecipeList;

@Mapper(componentModel = "spring", uses = RecipeMapper.class)
public interface RecipeListMapper {

    RecipeListViewDto entityToViewDto(RecipeList recipeList);
    RecipeList createDtoToEntity(RecipeListCreateDto recipeListCreateDto);
}
