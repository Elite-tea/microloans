package ru.hometask.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import ru.hometask.entities.Contract;

/**
 * Репозиторий для работы с договорами.
 * Поддерживает JPA спецификации для сложных запросов.
 */
@Repository
public interface ContractRepository extends JpaRepository<Contract, Long>, JpaSpecificationExecutor<Contract> {
    // Использую унаследованные методы из JpaRepository
}