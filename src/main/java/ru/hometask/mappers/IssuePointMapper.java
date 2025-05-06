package ru.hometask.mappers;

import org.mapstruct.Mapper;
import ru.hometask.dto.OldIssuePointDto;
import ru.hometask.entities.IssuePoint;

import java.util.List;

/**
 * Маппер для преобразования между сущностью IssuePoint и DTO.
 */
@Mapper(componentModel = "spring")
public interface IssuePointMapper {

    /**
     * Преобразует сущность IssuePoint в OldIssuePointDto.
     * @param issuePoint сущность пункта выдачи
     * @return DTO с данными пункта выдачи
     */
    OldIssuePointDto oldIssuePointMapper(IssuePoint issuePoint);

    /**
     * Преобразует список сущностей IssuePoint в список OldIssuePointDto.
     * @param issuePoint список сущностей пунктов выдачи
     * @return список DTO с данными пунктов выдачи
     */
    List<OldIssuePointDto> oldIssuePointListMapper(List<IssuePoint> issuePoint);
}