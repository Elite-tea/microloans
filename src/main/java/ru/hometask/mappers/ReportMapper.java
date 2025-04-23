package ru.hometask.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.hometask.dto.AdminReportDto;
import ru.hometask.entities.Contract;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ReportMapper {

    @Mapping(source = "issuePoint", target = "issuePointName")
    @Mapping(source = "status", target = "statusName")
    List<AdminReportDto> getContractReport (List<Contract> contracts);
}
