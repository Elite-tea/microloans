package ru.hometask.entities;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Table(name = "statuses")
@Getter
public class Status {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
}
