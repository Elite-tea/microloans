package ru.hometask.entities;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

/**
 * Сущность, представляющая договор между клиентом и компанией.
 * Содержит информацию о сторонах договора, финансовых условиях и сроках действия.
 */
@Entity
@Table(name = "contracts")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Contract {

    /**
     * Уникальный идентификатор договора
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Клиент, связанный с данным договором
     * Связь многие-к-одному с сущностью Client
     */
    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    /**
     * Сумма договора в базовой валюте системы
     */
    @Column(name = "amount", nullable = false, scale = 2)
    private double amount;

    /**
     * Дата и время заключения договора
     */
    @Column(name = "issue_date", nullable = false)
    private LocalDateTime dateOfIssue;

    /**
     * Сотрудник, оформивший договор
     * Связь многие-к-одному с сущностью Employee
     */
    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    /**
     * Дата окончания действия договора
     */
    @Column(name = "term_date")
    private LocalDateTime dateTerm;

    /**
     * Пункт выдачи, где был заключен договор
     * Связь многие-к-одному с сущностью IssuePoint
     */
    @ManyToOne
    @JoinColumn(name = "issue_point_id", nullable = false)
    private IssuePoint issuePoint;

    /**
     * Текущий статус договора
     * Связь многие-к-одному с сущностью Status
     */
    @ManyToOne
    @JoinColumn(name = "status_id", nullable = false)
    private Status status;
}
