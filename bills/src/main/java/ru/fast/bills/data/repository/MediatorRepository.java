package ru.fast.bills.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import ru.fast.bills.data.models.MediatorEntity;

import java.util.Optional;

public interface MediatorRepository extends JpaRepository<MediatorEntity, Long> {

    @Transactional(readOnly = true)
    Optional<MediatorEntity> findByServiceName(String serviceName);

    @Transactional(readOnly = true)
    boolean existsByServiceName(String serviceName);
}
