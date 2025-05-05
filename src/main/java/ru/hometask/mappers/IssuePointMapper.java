package ru.hometask.mappers;

import org.mapstruct.Mapper;
import ru.hometask.dto.OldIssuePointDto;
import ru.hometask.entities.IssuePoint;

import java.util.List;

@Mapper(componentModel = "spring")
public interface IssuePointMapper {

    OldIssuePointDto oldIssuePointMapper(IssuePoint issuePoint);

    List<OldIssuePointDto> oldIssuePointListMapper(List<IssuePoint> issuePoint);

    IssuePoint updateIssuePointMapper(OldIssuePointDto issuePoint);
}
