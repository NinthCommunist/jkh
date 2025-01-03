package ru.fast.bills.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.fast.bills.data.models.ExecutorEntity;

@Repository
public interface ExecutorRepository extends JpaRepository<ExecutorEntity, Long> {
}
