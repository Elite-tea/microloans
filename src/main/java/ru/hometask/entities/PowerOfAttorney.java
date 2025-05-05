package ru.hometask.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

/**
 * Сущность, представляющая доверенность.
 * Содержит реквизиты доверенности и дату её выдачи.
 */
@Entity
@Table(name = "power_of_attorneys")
@Getter
@Setter
public class PowerOfAttorney {

    /**
     * Уникальный идентификатор доверенности
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Номер доверенности (уникальный)
     */
    @Column(nullable = false, unique = true)
    private String number;

    /**
     * Дата выдачи доверенности
     */
    @Column(name = "date_poa", nullable = false)
    private LocalDateTime date;
}