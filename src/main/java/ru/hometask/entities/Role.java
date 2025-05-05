package ru.hometask.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * Сущность, представляющая роль сотрудника в системе.
 * Определяет уровень доступа и права пользователя.
 */
@Entity
@Table(name = "roles")
@Getter
@Setter
public class Role {

    /**
     * Уникальный идентификатор роли
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Название роли (например: "Администратор", "Менеджер")
     */
    @Column(nullable = false, unique = true)
    private String name;
}