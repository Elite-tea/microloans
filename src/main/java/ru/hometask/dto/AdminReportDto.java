package ru.hometask.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdminReportDto {

    String issuePointName;
    String statusName;
    Long allAmount;
    Long allCostContract;
}
