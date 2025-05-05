package ru.hometask.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * DTO для административного отчета.
 * Содержит агрегированные данные по договорам.
 */
@Getter
@Setter
public class AdminReportDto {
    /**
     * Название пункта выдачи
     */
    private String issuePointName;

    /**
     * Название статуса договора
     */
    private String statusName;

    /**
     * Общая сумма договоров
     */
    private BigDecimal allAmount;

    /**
     * Общее количество договоров
     */
    private Long allCostContract;
}