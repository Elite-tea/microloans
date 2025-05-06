package ru.hometask.services;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import ru.hometask.entities.Role;
import ru.hometask.repositories.RoleRepository;

import java.util.List;

/**
 * Сервис для работы с ролями сотрудников.
 */
@Service
@RequiredArgsConstructor
public class RoleService {
    private final RoleRepository roleRepository;

    /**
     * Получает все роли из системы.
     * @return Список всех ролей
     */
    @Cacheable("roles")
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }
}