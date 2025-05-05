package ru.hometask.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.hometask.entities.Role;
import ru.hometask.repositories.RoleRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleService {
    private final RoleRepository roleRepository;

    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

}