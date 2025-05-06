package ru.hometask.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.hometask.entities.Status;

/**
 * Репозиторий для работы со статусами договоров.
 */
@Repository
public interface StatusRepository extends JpaRepository<Status, Long> {
    // Использую унаследованные методы из JpaRepository
}