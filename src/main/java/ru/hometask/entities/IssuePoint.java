package ru.hometask.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * Сущность, представляющая пункт выдачи.
 * Содержит информацию о местоположении и названии пункта.
 */
@Entity
@Table(name = "issue_points")
@Getter
@Setter
public class IssuePoint {

    /**
     * Уникальный идентификатор пункта выдачи
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Физический адрес пункта выдачи
     */
    @Column(nullable = false)
    private String address;

    /**
     * Название пункта выдачи
     */
    @Column(nullable = false)
    private String name;
}