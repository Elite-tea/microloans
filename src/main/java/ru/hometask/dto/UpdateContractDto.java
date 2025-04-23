package ru.hometask.dto;

import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class UpdateContractDto {

    @Min(value = 1)
    private Long id;

    private Long clientId;

    private int amount;

    private LocalDateTime dateOfIssue;

    private Long employeeId;

    private LocalDateTime dateTerm;

    private Long issuePointId;

    private Long statusId;
}
