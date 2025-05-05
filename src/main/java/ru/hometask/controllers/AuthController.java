package ru.hometask.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.hometask.dto.RegistrationEmployeeDto;
import ru.hometask.entities.Employee;
import ru.hometask.entities.IssuePoint;
import ru.hometask.entities.PowerOfAttorney;
import ru.hometask.entities.Role;
import ru.hometask.repositories.EmployeeRepository;
import ru.hometask.repositories.IssuePointRepository;
import ru.hometask.repositories.PowerOfAttorneyRepository;
import ru.hometask.repositories.RoleRepository;

@RestController
@RequestMapping("/api/authR")
public class AuthController {

    private final EmployeeRepository employeeRepository;
    private final RoleRepository roleRepository;
    private final IssuePointRepository issuePointRepository;
    private final PowerOfAttorneyRepository powerOfAttorneyRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthController(EmployeeRepository employeeRepository,
                          RoleRepository roleRepository,
                          IssuePointRepository issuePointRepository,
                          PowerOfAttorneyRepository powerOfAttorneyRepository,
                          PasswordEncoder passwordEncoder) {
        this.employeeRepository = employeeRepository;
        this.roleRepository = roleRepository;
        this.issuePointRepository = issuePointRepository;
        this.powerOfAttorneyRepository = powerOfAttorneyRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/registration")
    public ResponseEntity<?> registerEmployee(@RequestBody RegistrationEmployeeDto registrationDto) {
        if (employeeRepository.existsByLogin(registrationDto.getLogin())) {
            return ResponseEntity.badRequest().body("Error: Login is already taken!");
        }

        Employee employee = new Employee();
        employee.setFullName(registrationDto.getFullName());
        employee.setLogin(registrationDto.getLogin());
        employee.setPassword(passwordEncoder.encode(registrationDto.getPassword()));

        // Установка роли
        Role role = roleRepository.findById(registrationDto.getRoleId())
                .orElseThrow(() -> new RuntimeException("Error: Role not found."));
        employee.setRole(role);

        // Установка пункта выдачи
        IssuePoint issuePoint = issuePointRepository.findById(registrationDto.getIssuePointId())
                .orElseThrow(() -> new RuntimeException("Error: IssuePoint not found."));
        employee.setIssuePoint(issuePoint);

        // Установка доверенности (если нужно)
        if (registrationDto.getPowerOfAttorneyId() != null) {
            PowerOfAttorney powerOfAttorney = powerOfAttorneyRepository.findById(registrationDto.getPowerOfAttorneyId())
                    .orElseThrow(() -> new RuntimeException("Error: PowerOfAttorney not found."));
            employee.setPowerOfAttorney(powerOfAttorney);
        }

        employeeRepository.save(employee);

        return ResponseEntity.ok("Employee registered successfully!");
    }
}
