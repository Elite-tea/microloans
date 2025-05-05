package ru.hometask.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * DTO для представления существующей доверенности.
 * Используется для операций с доверенностями.
 */
@Getter
@Setter
public class OldPowerOfAttorneyDto {

    /**
     * Идентификатор доверенности (должен быть ≥ 0)
     */
    @Min(value = 0)
    private Long id;

    /**
     * Номер доверенности (обязательное поле)
     */
    @NotBlank
    private String number;

    /**
     * Дата выдачи доверенности
     */
    private LocalDateTime date;
}