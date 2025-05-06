package ru.hometask.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import ru.hometask.entities.Client;

/**
 * Репозиторий для работы с клиентами.
 * Поддерживает JPA спецификации для сложных запросов.
 */
@Repository
public interface ClientRepository extends JpaRepository<Client, Long>, JpaSpecificationExecutor<Client> {
    // Использую унаследованные методы из JpaRepository
}