package ru.hometask.microloans.entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "contracts")
public class Contract {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_client")
    private Client client;

    private int amount;

    private LocalDateTime dateOfIssue;

    @ManyToOne
    @JoinColumn(name = "id_employee")
    private Employee employee;

    private LocalDateTime dateTerm;

    @ManyToOne
    @JoinColumn(name = "id_pickup_points")
    private IssuePoint issuePoint;

    @ManyToOne
    @JoinColumn(name = "id_status")
    private Status status;

}
