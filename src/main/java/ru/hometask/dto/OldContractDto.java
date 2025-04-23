package ru.hometask.dto;

import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;
import ru.hometask.entities.Client;
import ru.hometask.entities.IssuePoint;
import ru.hometask.entities.Status;

import java.time.LocalDateTime;

@Setter
@Getter
public class OldContractDto {

    @Min(value = 0)
    private Long id;

    private Client client;

    private Long amount;

    private LocalDateTime dateOfIssue;

    private GetEmployeeDto employee;

    private LocalDateTime dateTerm;

    private IssuePoint issuePoint;

    private Status status;

}