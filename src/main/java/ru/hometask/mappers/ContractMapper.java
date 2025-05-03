package ru.hometask.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.hometask.dto.NewContractDto;
import ru.hometask.dto.OldContractDto;
import ru.hometask.dto.UpdateContractDto;
import ru.hometask.entities.Contract;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ContractMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(source = "clientId", target = "client.id")
    @Mapping(source = "employeeId", target = "employee.id")
    @Mapping(source = "issuePointId", target = "issuePoint.id")
    @Mapping(source = "statusId", target = "status.id")
    Contract newContractMapping(NewContractDto newContract);

    @Mapping(source = "client.id", target = "clientId")
    @Mapping(source = "employee.id", target = "employeeId")
    @Mapping(source = "issuePoint.id", target = "issuePointId")
    @Mapping(source = "status.id", target = "statusId")
    UpdateContractDto oldContractMapping(Contract contract);

    @Mapping(source = "client.id", target = "clientId")
    @Mapping(source = "employee.id", target = "employeeId")
    @Mapping(source = "issuePoint.id", target = "issuePointId")
    @Mapping(source = "status.id", target = "statusId")
    UpdateContractDto oldContractToUpdateMapping(OldContractDto contract);

    @Mapping(source = "employee", target = "employee")
    List<OldContractDto> noPasswordContractDTO(List<Contract> contract);

}