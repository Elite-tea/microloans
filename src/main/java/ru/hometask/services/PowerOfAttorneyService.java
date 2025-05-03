package ru.hometask.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.hometask.entities.PowerOfAttorney;
import ru.hometask.repositories.PowerOfAttorneyRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PowerOfAttorneyService {
    private final PowerOfAttorneyRepository powerOfAttorneyRepository;

    public List<PowerOfAttorney> getAllPowerOfAttorneys() {
        return powerOfAttorneyRepository.findAll();
    }
}
