package ru.hometask.services;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.hometask.dto.OldEmployeeDto;
import ru.hometask.dto.UpdateEmployeeDto;
import ru.hometask.entities.Employee;
import ru.hometask.entities.IssuePoint;
import ru.hometask.entities.PowerOfAttorney;
import ru.hometask.entities.Role;
import ru.hometask.mappers.EmployeeMapper;
import ru.hometask.repositories.EmployeeRepository;
import ru.hometask.repositories.IssuePointRepository;
import ru.hometask.repositories.PowerOfAttorneyRepository;
import ru.hometask.repositories.RoleRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final EmployeeMapper employeeMapper;
    private final IssuePointRepository issuePointRepository;
    private final RoleRepository roleRepository;
    private final PowerOfAttorneyRepository powerOfAttorneyRepository;

    public OldEmployeeDto getEmployee (Long idEmployee) {
        Employee employee = employeeRepository.getReferenceById(idEmployee);
        OldEmployeeDto oldEmployee = employeeMapper.oldEmployeeMapping(employee);

        oldEmployee.setIssuePointId(employee.getIssuePoint().getId());
        oldEmployee.setRoleId(employee.getRole().getId());
        oldEmployee.setPowerOfAttorneyId(employee.getPowerOfAttorney().getId());
        return oldEmployee;
    }

    public List<OldEmployeeDto> getAllEmployee () {
        List<Employee> employees = employeeRepository.findAll();
        return employeeMapper.toUpdateDtoList(employees);
    }

    @Transactional
    public UpdateEmployeeDto updateEmployee (UpdateEmployeeDto updateEmployee) {
        Employee employee = employeeRepository.findById(updateEmployee.getId())
                .orElseThrow(() -> new EntityNotFoundException("Сотрудник не найден"));
        updateFromDto(updateEmployee, employee);
        return updateEmployee;
    }

    private void updateFromDto(UpdateEmployeeDto dto, Employee employee) {
        if (dto == null || employee == null) return;

        if (dto.getIssuePointId() != null) {
            IssuePoint issuePoint = issuePointRepository.findById(dto.getIssuePointId())
                            .orElseThrow(() -> new EntityNotFoundException("Точка выдачи не найдена"));
            employee.setIssuePoint(issuePoint);
        }

        if (dto.getFullName() != null) {
            employee.setFullName(dto.getFullName());
        }

        if (dto.getPassword() != null) {
            employee.setPassword(dto.getPassword());
        }

        if (dto.getLogin() != null) {
            employee.setLogin(dto.getLogin());
        }

        if (dto.getRoleId() != null) {
            Role role = roleRepository.findById(dto.getRoleId())
                            .orElseThrow(() -> new EntityNotFoundException("Роль не найдена"));

            employee.setRole(role);
        }

        if (dto.getPowerOfAttorneyId() != null) {
            PowerOfAttorney powerOfAttorney = powerOfAttorneyRepository.findById(dto.getPowerOfAttorneyId())
                            .orElseThrow(() -> new EntityNotFoundException("Доверенность не найдена"));
            employee.setPowerOfAttorney(powerOfAttorney);
        }

    }
}
