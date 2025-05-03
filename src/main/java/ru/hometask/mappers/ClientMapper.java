package ru.hometask.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.hometask.dto.NewClientDto;
import ru.hometask.dto.OldClientDto;
import ru.hometask.entities.Client;

@Mapper(componentModel = "spring")
public interface ClientMapper {

    @Mapping(target = "id", ignore = true)
    Client newUserMapping(NewClientDto newClient);

    OldClientDto oldUserMapping(Client client);
}
