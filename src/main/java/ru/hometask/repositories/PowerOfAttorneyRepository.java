package ru.hometask.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import ru.hometask.entities.PowerOfAttorney;

/**
 * Репозиторий для работы с доверенностями.
 * Поддерживает JPA спецификации для сложных запросов.
 */
@Repository
public interface PowerOfAttorneyRepository extends JpaRepository<PowerOfAttorney, Long>, JpaSpecificationExecutor<PowerOfAttorney> {
// Использую базовые методы JpaRepository и JpaSpecificationExecutor
}