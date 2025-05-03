package ru.hometask.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateEmployeeDto {

    @Min(value = 1)
    private Long id;

    private String login;

    private String password;

    private Long roleId;

    private Long powerOfAttorneyId;

    private Long issuePointId;

    @NotBlank
    private String fullName;
}
