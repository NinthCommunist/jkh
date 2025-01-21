package ru.fast.bills.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.fast.bills.data.models.ClaimEntity;

import java.util.List;
import java.util.UUID;

@Repository
public interface ClaimRepository extends JpaRepository<ClaimEntity, UUID> {
    List<ClaimEntity> findAllByUser_Id(UUID id);
}
