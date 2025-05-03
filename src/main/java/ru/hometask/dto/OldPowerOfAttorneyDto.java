package ru.hometask.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;

public class OldPowerOfAttorneyDto {

    @Min(value = 0)
    private Long id;

    @NotBlank
    private String number;


    private LocalDateTime date;
}
