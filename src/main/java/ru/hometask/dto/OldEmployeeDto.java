package ru.hometask.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

/**
 * DTO для представления существующего сотрудника.
 * Используется для операций обновления данных сотрудника.
 */
@Getter
@Setter
public class OldEmployeeDto {

    /**
     * Идентификатор сотрудника
     */
    private Long id;

    /**
     * Логин сотрудника
     */
    private String login;

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