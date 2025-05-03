package ru.hometask.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.hometask.dto.OldEmployeeDto;
import ru.hometask.dto.UpdateEmployeeDto;
import ru.hometask.entities.Employee;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {


    @Mapping(source = "powerOfAttorney.id", target = "powerOfAttorneyId")
    @Mapping(source = "issuePoint.id", target = "issuePointId")
    @Mapping(source = "role.id", target = "roleId")
    OldEmployeeDto oldEmployeeMapping(Employee employee);

    List<OldEmployeeDto> toUpdateDtoList(List<Employee> employees);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "powerOfAttorneyId", source = "powerOfAttorney.id")
    @Mapping(target = "issuePointId", source = "issuePoint.id")
    @Mapping(target = "roleId", source = "role.id")
    List<UpdateEmployeeDto> toUpdateEmployeeDtoList(List<OldEmployeeDto> employees);
}