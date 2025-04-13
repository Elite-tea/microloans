package ru.hometask.microloans.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "employees")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fullName;

    private char login;

    private char password;

    @OneToOne
    @JoinColumn(name = "id_role")
    private Role role;

    @OneToMany
    @JoinColumn(name = "id_power_of_attorney")
    private PowerOfAttorney PowerOfAttorney;
}
