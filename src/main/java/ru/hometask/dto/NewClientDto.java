package ru.hometask.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

/**
 * DTO для создания нового клиента.
 * Содержит валидацию входных данных.
 */
@Getter
@Setter
public class NewClientDto {
    /**
     * Полное имя клиента (2-250 символов)
     */
    @NotBlank
    @Length(min = 2, max = 250)
    private String fullName;

    /**
     * Телефон клиента (11-16 символов)
     */
    @NotBlank
    @Length(min = 11, max = 16)
    private String phone;
}