package ru.hometask.dto;

import lombok.Getter;
import lombok.Setter;
import ru.hometask.entities.IssuePoint;
import ru.hometask.entities.PowerOfAttorney;
import ru.hometask.entities.Role;

@Getter
@Setter
public class GetEmployeeDto {
    private Long id;

    private String fullName;

    private String login;

    private Role role;

    private IssuePoint issuePoint;

    private PowerOfAttorney PowerOfAttorney;
}
