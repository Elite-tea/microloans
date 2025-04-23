package ru.hometask.mappers;

import org.mapstruct.Mapper;
import ru.hometask.dto.OldIssuePointDto;
import ru.hometask.entities.IssuePoint;

@Mapper(componentModel = "spring")
public interface IssuePointMapper {

    OldIssuePointDto oldIssuePointMapper(IssuePoint issuePoint);
}
