package ru.hometask.dto;

import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CloseStatusContractDto {

    @Min(value = 1)
    private Long id;

    private Long statusId;
}
