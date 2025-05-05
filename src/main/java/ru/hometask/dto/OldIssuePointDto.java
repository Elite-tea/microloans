package ru.hometask.dto;

import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;

/**
 * DTO для представления существующего пункта выдачи.
 * Используется для операций обновления и получения данных.
 */
@Getter
@Setter
public class OldIssuePointDto {

    /**
     * Идентификатор пункта выдачи (должен быть ≥ 1)
     */
    @Min(value = 1)
    private Long id;

    /**
     * Адрес пункта выдачи
     */
    private String address;

    /**
     * Название пункта выдачи
     */
    private String name;
}