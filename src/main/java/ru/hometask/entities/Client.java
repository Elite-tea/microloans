package ru.hometask.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Сущность, представляющая клиента системы.
 * Содержит персональную информацию о клиенте, включая ФИО и контактные данные.
 */
@Entity
@Table(name = "clients")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Client {

    /**
     * Уникальный идентификатор клиента
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Полное имя клиента в формате "Фамилия Имя Отчество"
     * Хранится в колонке 'full_name'
     */
    @Column(name = "full_name", nullable = false)
    private String fullName;

    /**
     * Контактный телефон клиента в международном формате
     */
    @Column(name = "phone", nullable = false, unique = true)
    private String phone;
}