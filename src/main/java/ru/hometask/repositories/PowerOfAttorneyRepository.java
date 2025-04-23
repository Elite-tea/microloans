package ru.hometask.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import ru.hometask.entities.PowerOfAttorney;

import java.util.List;
import java.util.Optional;

public interface PowerOfAttorneyRepository extends JpaRepository<PowerOfAttorney, Long>, JpaSpecificationExecutor<PowerOfAttorney> {
    Optional<PowerOfAttorney> findById(Long id);
    List<PowerOfAttorney> findAll();
}
