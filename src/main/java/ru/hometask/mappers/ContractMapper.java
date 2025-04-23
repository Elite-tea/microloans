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
    Contract newContractMapping(NewContractDto newContract);

    UpdateContractDto oldContractMapping(Contract contract);

    @Mapping(source = "employee", target = "employee")
    List<OldContractDto> noPasswordContractDTO(List<Contract> contract);

}