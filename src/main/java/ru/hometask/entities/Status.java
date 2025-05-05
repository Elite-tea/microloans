package ru.hometask.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * Сущность, представляющая статус договора.
 * Определяет текущее состояние договора (например: "Активен", "Завершен").
 */
@Entity
@Table(name = "statuses")
@Getter
@Setter
public class Status {

    /**
     * Уникальный идентификатор статуса
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Название статуса
     */
    @Column(nullable = false, unique = true)
    private String name;
}