package ru.hometask.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

/**
 * DTO для обновления данных сотрудника.
 * Содержит поля, доступные для изменения.
 */
@Getter
@Setter
public class UpdateEmployeeDto {

    /**
     * Идентификатор сотрудника (должен быть ≥ 1)
     */
    @Min(value = 1)
    private Long id;

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
     * Идентификатор доверенности сотрудника
     */
    private Long powerOfAttorneyId;

    /**
     * Идентификатор пункта выдачи сотрудника
     */
    private Long issuePointId;

    /**
     * Полное имя сотрудника (обязательное поле)
     */
    @NotBlank
    private String fullName;
}