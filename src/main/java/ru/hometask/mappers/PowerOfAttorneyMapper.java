package ru.hometask.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import ru.hometask.dto.OldPowerOfAttorneyDto;
import ru.hometask.entities.PowerOfAttorney;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface PowerOfAttorneyMapper {

    OldPowerOfAttorneyDto oldPowerOdAttorney(PowerOfAttorney powerOfAttorney);
}
