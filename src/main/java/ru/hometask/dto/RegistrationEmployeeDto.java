package ru.hometask.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * DTO для регистрации нового сотрудника.
 * Содержит данные, необходимые для создания учетной записи сотрудника.
 */
@Getter
@Setter
public class RegistrationEmployeeDto {
    /**
     * Полное имя сотрудника
     */
    private String fullName;

    /**
     * Логин сотрудника
     */
    private String login;

    /**
     * Пароль сотрудника
     */
    private String password;

    /**
     * Идентификатор роли сотрудника
     */
    private Long roleId;

    /**
     * Идентификатор пункта выдачи сотрудника
     */
    private Long issuePointId;

    /**
     * Идентификатор доверенности сотрудника
     */
    private Long powerOfAttorneyId;
}