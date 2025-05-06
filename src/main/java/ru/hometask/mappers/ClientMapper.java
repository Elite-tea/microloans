package ru.hometask.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.hometask.dto.NewClientDto;
import ru.hometask.dto.OldClientDto;
import ru.hometask.entities.Client;

/**
 * Маппер для преобразования между сущностью Client и DTO.
 */
@Mapper(componentModel = "spring")
public interface ClientMapper {

    /**
     * Преобразует NewClientDto в сущность Client.
     * Игнорирует поле id, так как оно генерируется автоматически.
     * @param newClient DTO для создания нового клиента
     * @return сущность Client
     */
    @Mapping(target = "id", ignore = true)
    Client newUserMapping(NewClientDto newClient);

    /**
     * Преобразует сущность Client в OldClientDto.
     * @param client сущность клиента
     * @return DTO с данными клиента
     */
    OldClientDto oldUserMapping(Client client);
}