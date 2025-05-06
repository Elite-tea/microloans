package ru.hometask.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.hometask.dto.NewContractDto;
import ru.hometask.dto.OldContractDto;
import ru.hometask.dto.UpdateContractDto;
import ru.hometask.entities.Contract;

import java.util.List;

/**
 * Маппер для преобразования между сущностью Contract и DTO.
 */
@Mapper(componentModel = "spring")
public interface ContractMapper {

    /**
     * Преобразует NewContractDto в сущность Contract.
     * @param newContract DTO для создания нового договора
     * @return сущность Contract
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(source = "clientId", target = "client.id")
    @Mapping(source = "employeeId", target = "employee.id")
    @Mapping(source = "issuePointId", target = "issuePoint.id")
    @Mapping(source = "statusId", target = "status.id")
    Contract newContractMapping(NewContractDto newContract);

    /**
     * Преобразует сущность Contract в UpdateContractDto.
     * @param contract сущность договора
     * @return DTO для обновления договора
     */
    @Mapping(source = "client.id", target = "clientId")
    @Mapping(source = "employee.id", target = "employeeId")
    @Mapping(source = "issuePoint.id", target = "issuePointId")
    @Mapping(source = "status.id", target = "statusId")
    UpdateContractDto oldContractMapping(Contract contract);

    /**
     * Преобразует OldContractDto в UpdateContractDto.
     * @param contract DTO с данными договора
     * @return DTO для обновления договора
     */
    @Mapping(source = "client.id", target = "clientId")
    @Mapping(source = "employee.id", target = "employeeId")
    @Mapping(source = "issuePoint.id", target = "issuePointId")
    @Mapping(source = "status.id", target = "statusId")
    UpdateContractDto oldContractToUpdateMapping(OldContractDto contract);

    /**
     * Преобразует список сущностей Contract в список OldContractDto.
     * @param contract список сущностей договоров
     * @return список DTO с данными договоров
     */
    List<OldContractDto> noPasswordContractDTO(List<Contract> contract);
}