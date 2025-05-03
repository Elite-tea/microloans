package ru.hometask.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OldClientDto {

    @Min(value = 1)
    private Long id;

    private String phone;

    private String fullName;
}
