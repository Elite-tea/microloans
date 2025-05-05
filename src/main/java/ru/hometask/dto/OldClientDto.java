package ru.hometask.dto;

import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;

/**
 * DTO для представления существующего клиента.
 * Используется для операций обновления и получения данных.
 */
@Getter
@Setter
public class OldClientDto {

    /**
     * Идентификатор клиента (должен быть ≥ 1)
     */
    @Min(value = 1)
    private Long id;

    /**
     * Телефон клиента
     */
    private String phone;

    /**
     * Полное имя клиента
     */
    private String fullName;
}