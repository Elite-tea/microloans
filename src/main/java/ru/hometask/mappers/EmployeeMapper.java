package ru.hometask.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.hometask.dto.OldEmployeeDto;
import ru.hometask.dto.UpdateEmployeeDto;
import ru.hometask.entities.Employee;

import java.util.List;

/**
 * Маппер для преобразования между сущностью Employee и DTO.
 */
@Mapper(componentModel = "spring")
public interface EmployeeMapper {

    /**
     * Преобразует сущность Employee в OldEmployeeDto.
     * @param employee сущность сотрудника
     * @return DTO с данными сотрудника
     */
    @Mapping(source = "powerOfAttorney.id", target = "powerOfAttorneyId")
    @Mapping(source = "issuePoint.id", target = "issuePointId")
    @Mapping(source = "role.id", target = "roleId")
    OldEmployeeDto oldEmployeeMapping(Employee employee);

    /**
     * Преобразует список сущностей Employee в список OldEmployeeDto.
     * @param employees список сущностей сотрудников
     * @return список DTO с данными сотрудников
     */
    List<OldEmployeeDto> toUpdateDtoList(List<Employee> employees);

    /**
     * Преобразует список OldEmployeeDto в список UpdateEmployeeDto.
     * @param employees список DTO сотрудников
     * @return список DTO для обновления сотрудников
     */
    @Mapping(target = "id", source = "id")
    @Mapping(target = "powerOfAttorneyId", source = "powerOfAttorney.id")
    @Mapping(target = "issuePointId", source = "issuePoint.id")
    @Mapping(target = "roleId", source = "role.id")
    List<UpdateEmployeeDto> toUpdateEmployeeDtoList(List<OldEmployeeDto> employees);
}