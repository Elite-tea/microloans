package ru.hometask.mappers;

import org.mapstruct.Mapper;
import ru.hometask.dto.OldEmployeeDto;
import ru.hometask.entities.Employee;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {

    OldEmployeeDto oldEmployeeMapping(Employee employee);

    List<OldEmployeeDto> toUpdateDtoList(List<Employee> employees);
}