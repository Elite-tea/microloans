package ru.hometask.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import ru.hometask.entities.IssuePoint;

@Repository
public interface IssuePointRepository extends JpaRepository<IssuePoint, Long>, JpaSpecificationExecutor<IssuePoint> {

}
