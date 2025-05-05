package ru.hometask.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.hometask.dto.AdminReportDto;
import ru.hometask.dto.OldEmployeeDto;
import ru.hometask.dto.OldIssuePointDto;
import ru.hometask.dto.UpdateEmployeeDto;
import ru.hometask.entities.IssuePoint;
import ru.hometask.services.EmployeeService;
import ru.hometask.services.IssuePointService;
import ru.hometask.services.ReportService;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
@Slf4j
public class AdminIssuePointController {
    private final IssuePointService issuePointService;
    private final EmployeeService employeeService;
    private final ReportService reportService;

    @GetMapping("/issue-points/{idIssuePoints}")
    @ResponseStatus(HttpStatus.OK)
    public OldIssuePointDto getIssuePoints (@PathVariable Long idIssuePoints) {
        log.info("Я ПОЛУЧИЛ И ВЕРНУЛ ТОЧКУ!");
        return issuePointService.getIssuePoint(idIssuePoints);
    }

    @GetMapping("/issue-points")
    @ResponseStatus(HttpStatus.OK)
    public List<IssuePoint> getIssuePoints() {
        log.info("Отдал все точки");
        return issuePointService.getAllIssuePoint();
    }

    @PutMapping("/issue-points/{idIssuePoints}")
    @ResponseStatus(HttpStatus.OK)
    public OldIssuePointDto updateIssuePoints(@RequestBody @Valid OldIssuePointDto idIssuePoints) {
        log.info("Точка обновлена");
        return issuePointService.updateIssuePoint(idIssuePoints);
    }

    @GetMapping("/employees/{idEmployee}")
    @ResponseStatus(HttpStatus.OK)
    public OldEmployeeDto getEmployee (@PathVariable Long idEmployee) {
        log.info("Отдал данные сотрудника");
        return employeeService.getEmployee(idEmployee);
    }

    @GetMapping("/employees")
    @ResponseStatus(HttpStatus.OK)
    public List<OldEmployeeDto> getAllEmployee () {
        log.info("Отдал всех пользователей");
        return employeeService.getAllEmployee();
    }

    @PutMapping("/employees/{idEmployee}")
    @ResponseStatus(HttpStatus.OK)
    public UpdateEmployeeDto updateEmployee (@RequestBody @Valid UpdateEmployeeDto updateEmployee) {
        log.info("Обновил пользователя");
        return employeeService.updateEmployee(updateEmployee);
    }

    @GetMapping("/report")
    @ResponseStatus(HttpStatus.OK)
    public List<AdminReportDto> report () {
        log.info("Отдали полный отчет под админом");
        return reportService.getReportActive();
    }
}