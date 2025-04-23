package ru.hometask.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "power_of_attorneys")
@Getter
@Setter
public class PowerOfAttorney {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String number;

    @Column(name = "date_poa")
    private LocalDateTime date;
}
