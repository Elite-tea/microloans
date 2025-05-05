package ru.hometask.dto;

import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class UpdateContractDto {

    @Min(value = 1)
    private Long id;

    private Long clientId;

    private BigDecimal amount;

    private LocalDateTime dateOfIssue;

    private Long employeeId;

    private LocalDateTime dateTerm;

    private Long issuePointId;

    private Long statusId;
}
