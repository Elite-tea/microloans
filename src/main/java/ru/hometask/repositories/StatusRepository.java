package ru.hometask.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.hometask.entities.Status;

public interface StatusRepository  extends JpaRepository<Status, Long> {
}
