package ru.hometask.microloans.entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "power_of_attorneys")
public class PowerOfAttorney {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int number;

    private LocalDateTime date;
}
