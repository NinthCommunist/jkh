package ru.fast.bills.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.fast.bills.data.models.MediatorEntity;

public interface MediatorRepository extends JpaRepository<MediatorEntity, Long> {
}
