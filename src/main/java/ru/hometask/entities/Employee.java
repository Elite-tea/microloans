package ru.hometask.entities;

import jakarta.persistence.*;
import lombok.*;

/**
 * Сущность, представляющая сотрудника компании.
 * Содержит учетные данные и информацию о должности сотрудника.
 */
@Entity
@Table(name = "employees")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Employee {

    /**
     * Уникальный идентификатор сотрудника
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Полное имя сотрудника в формате "Фамилия Имя Отчество"
     */
    @Column(name = "full_name", nullable = false)
    private String fullName;

    /**
     * Логин для входа в систему (уникальный)
     */
    @Column(name = "login", nullable = false, unique = true)
    private String login;

    /**
     * Пароль для входа в систему (должен храниться в зашифрованном виде)
     */
    @Column(name = "password", nullable = false)
    private String password;

    /**
     * Роль сотрудника в системе
     * Связь многие-к-одному с сущностью Role
     */
    @ManyToOne
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;

    /**
     * Пункт выдачи, к которому прикреплен сотрудник
     * Связь многие-к-одному с сущностью IssuePoint
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "issue_point_id", nullable = false)
    private IssuePoint issuePoint;

    /**
     * Доверенность сотрудника (если имеется)
     * Связь многие-к-одному с сущностью PowerOfAttorney
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "power_of_attorney_id")
    private PowerOfAttorney powerOfAttorney;
}