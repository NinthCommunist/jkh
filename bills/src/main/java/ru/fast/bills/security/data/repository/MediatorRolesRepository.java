package ru.fast.bills.security.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.fast.bills.security.data.models.MediatorAuthority;
import ru.fast.bills.security.data.models.MediatorRoleEntity;

import java.util.Collection;
import java.util.Optional;

public interface MediatorRolesRepository extends JpaRepository<MediatorRoleEntity, Long> {
    Optional<MediatorRoleEntity> findAllByAuthority(MediatorAuthority mediatorAuthorities);

    Collection<MediatorRoleEntity> findByMediators_Id(Long mediatorId);
}
