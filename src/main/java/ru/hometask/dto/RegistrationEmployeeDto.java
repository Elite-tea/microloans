package ru.hometask.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegistrationEmployeeDto {
    private String fullName;
    private String login;
    private String password;
    private Long roleId;
    private Long issuePointId;
    private Long powerOfAttorneyId;
}
