package ru.hometask.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OldIssuePointDto {

    @Min(value = 1)
    private Long id;
    
    private String address;

    private String name;
}
