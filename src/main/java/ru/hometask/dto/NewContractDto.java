package ru.hometask.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * DTO для создания нового договора.
 * Содержит валидацию и форматирование дат.
 */
@Setter
@Getter
public class NewContractDto {
    /**
     * Идентификатор клиента
     */
    private Long clientId;

    /**
     * Сумма договора
     */
    private BigDecimal amount;

    /**
     * Дата заключения договора (в будущем)
     */
    @NotNull
    @Future
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime dateOfIssue;

    /**
     * Идентификатор сотрудника
     */
    private Long employeeId;

    /**
     * Дата окончания договора (в будущем)
     */
    @NotNull
    @Future
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime dateTerm;

    /**
     * Идентификатор пункта выдачи
     */
    private Long issuePointId;

    /**
     * Идентификатор статуса
     */
    private Long statusId;
}