package ru.hometask.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.hometask.entities.Role;

/**
 * Репозиторий для работы с ролями сотрудников.
 */
@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    // Использую унаследованные методы из JpaRepository
}