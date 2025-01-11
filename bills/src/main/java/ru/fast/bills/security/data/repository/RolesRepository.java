package ru.fast.bills.security.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.fast.bills.security.data.models.MediatorRoleEntity;

public interface RolesRepository extends JpaRepository<MediatorRoleEntity, Long> {
}
