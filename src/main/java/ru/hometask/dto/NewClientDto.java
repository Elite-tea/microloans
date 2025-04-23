package ru.hometask.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
public class NewClientDto {

    @NotBlank
    @Length(min = 2, max = 250)
    private String fullName;


    @NotBlank
    @Length(min = 11, max = 16)
    private String phone;
}