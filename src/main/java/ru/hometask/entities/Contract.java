package ru.hometask.entities;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "contracts")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Contract {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    @Column(name = "amount")
    private double amount;

    @Column(name = "issue_date")
    private LocalDateTime dateOfIssue;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @Column(name = "term_date")
    private LocalDateTime dateTerm;

    @ManyToOne
    @JoinColumn(name = "issue_point_id")
    private IssuePoint issuePoint;

    @ManyToOne
    @JoinColumn(name = "status_id")
    private Status status;

}
