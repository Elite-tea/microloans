package ru.hometask.dto;

import lombok.Getter;
import lombok.Setter;
import ru.hometask.entities.IssuePoint;
import ru.hometask.entities.PowerOfAttorney;
import ru.hometask.entities.Role;

/**
 * DTO для получения информации о сотруднике.
 * Содержит основные данные и связанные сущности.
 */
@Getter
@Setter
public class GetEmployeeDto {
    /**
     * Идентификатор сотрудника
     */
    private Long id;

    /**
     * Полное имя сотрудника
     */
    private String fullName;

    /**
     * Логин сотрудника
     */
    private String login;

    /**
     * Роль сотрудника
     */
    private Role role;

    /**
     * Пункт выдачи сотрудника
     */
    private IssuePoint issuePoint;

    /**
     * Доверенность сотрудника
     */
    private PowerOfAttorney powerOfAttorney;
}