package com.algorceries.api.mapper;

import org.mapstruct.Mapper;
import com.algorceries.api.dto.HouseholdCreateDto;
import com.algorceries.api.dto.HouseholdViewDto;
import com.algorceries.api.entity.Household;

@Mapper(componentModel = "spring")
public interface HouseholdMapper {

    HouseholdViewDto entityToViewDto(Household household);
    Household createDtoToEntity(HouseholdCreateDto householdCreateDto);
}
