package ru.hometask.microloans.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "pickup_points")
public class IssuePoint {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String address;

    private String name;
}
