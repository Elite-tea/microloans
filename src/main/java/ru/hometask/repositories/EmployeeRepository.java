package ru.hometask.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.hometask.entities.Employee;

import java.util.Optional;

/**
 * Репозиторий для работы с сотрудниками.
 * Содержит методы для аутентификации и проверки существования.
 */
@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    /**
     * Находит сотрудника по логину.
     * @param login логин сотрудника
     * @return Optional с сотрудником, если найден
     */
    Optional<Employee> findByLogin(String login);

    /**
     * Проверяет существование сотрудника с указанным логином.
     * @param login логин для проверки
     * @return true если сотрудник с таким логином существует
     */
    boolean existsByLogin(String login);
}