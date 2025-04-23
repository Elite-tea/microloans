package ru.hometask.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.hometask.entities.Role;

public interface RoleRepository   extends JpaRepository<Role, Long> {
}