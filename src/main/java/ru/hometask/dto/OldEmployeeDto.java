package ru.hometask.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OldEmployeeDto {

    private Long id;

    private String login;

    private Long roleId;

    private Long powerOfAttorneyId;

    private Long issuePointId;

    @NotBlank
    private String fullName;
}
