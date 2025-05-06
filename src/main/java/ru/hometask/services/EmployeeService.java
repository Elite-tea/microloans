package ru.hometask.services;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.hometask.dto.OldEmployeeDto;
import ru.hometask.dto.UpdateEmployeeDto;
import ru.hometask.entities.Employee;
import ru.hometask.mappers.EmployeeMapper;
import ru.hometask.repositories.EmployeeRepository;
import ru.hometask.repositories.IssuePointRepository;
import ru.hometask.repositories.PowerOfAttorneyRepository;
import ru.hometask.repositories.RoleRepository;

import java.util.List;

/**
 * Сервис для работы с сотрудниками.
 * Обеспечивает операции CRUD и бизнес-логику связанную с сотрудниками.
 */
@Service
@RequiredArgsConstructor
public class EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final EmployeeMapper employeeMapper;
    private final IssuePointRepository issuePointRepository;
    private final RoleRepository roleRepository;
    private final PowerOfAttorneyRepository powerOfAttorneyRepository;

    /**
     * Получает данные сотрудника по ID.
     * @param employeeId ID сотрудника
     * @return DTO с данными сотрудника
     * @throws EntityNotFoundException если сотрудник не найден
     */
    public OldEmployeeDto getEmployeeById(Long employeeId) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new EntityNotFoundException("Сотрудник с ID " + employeeId + " не найден"));
        return employeeMapper.oldEmployeeMapping(employee);
    }

    /**
     * Получает список всех сотрудников.
     * @return список DTO сотрудников
     */
    public List<OldEmployeeDto> getAllEmployees() {
        return employeeMapper.toUpdateDtoList(employeeRepository.findAll());
    }

    /**
     * Обновляет данные сотрудника.
     * @param updateDto DTO с обновленными данными
     * @return обновленное DTO
     * @throws EntityNotFoundException если сотрудник или связанные сущности не найдены
     */
    @Transactional
    public UpdateEmployeeDto updateEmployee(UpdateEmployeeDto updateDto) {
        Employee employee = employeeRepository.findById(updateDto.getId())
                .orElseThrow(() -> new EntityNotFoundException("Сотрудник не найден"));

        updateEmployeeFromDto(updateDto, employee);
        employeeRepository.save(employee);
        return updateDto;
    }

    private void updateEmployeeFromDto(UpdateEmployeeDto dto, Employee employee) {
        if (dto.getFullName() != null) {
            employee.setFullName(dto.getFullName());
        }
        if (dto.getLogin() != null) {
            employee.setLogin(dto.getLogin());
        }
        if (dto.getPassword() != null) {
            employee.setPassword(dto.getPassword());
        }
        if (dto.getRoleId() != null) {
            employee.setRole(roleRepository.findById(dto.getRoleId())
                    .orElseThrow(() -> new EntityNotFoundException("Роль не найдена")));
        }
        if (dto.getIssuePointId() != null) {
            employee.setIssuePoint(issuePointRepository.findById(dto.getIssuePointId())
                    .orElseThrow(() -> new EntityNotFoundException("Пункт выдачи не найден")));
        }
        if (dto.getPowerOfAttorneyId() != null) {
            employee.setPowerOfAttorney(powerOfAttorneyRepository.findById(dto.getPowerOfAttorneyId())
                    .orElseThrow(() -> new EntityNotFoundException("Доверенность не найдена")));
        }
    }
}