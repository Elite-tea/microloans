package ru.hometask.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.hometask.entities.PowerOfAttorney;
import ru.hometask.repositories.PowerOfAttorneyRepository;

import java.util.List;

/**
 * Сервис для работы с доверенностями.
 */
@Service
@RequiredArgsConstructor
public class PowerOfAttorneyService {
    private final PowerOfAttorneyRepository powerOfAttorneyRepository;

    /**
     * Получает все доверенности.
     * @return список доверенностей
     */
    public List<PowerOfAttorney> getAllPowerOfAttorneys() {
        return powerOfAttorneyRepository.findAll();
    }
}