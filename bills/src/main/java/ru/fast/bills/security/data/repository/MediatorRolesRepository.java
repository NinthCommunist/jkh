package ru.fast.bills.security.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.fast.bills.security.data.models.MediatorAuthority;
import ru.fast.bills.security.data.models.MediatorRoleEntity;

import java.util.Collection;

public interface MediatorRolesRepository extends JpaRepository<MediatorRoleEntity, Long> {
    Collection<MediatorRoleEntity> findAllByAuthorityIn(Iterable<MediatorAuthority> mediatorAuthorities);

    Collection<MediatorRoleEntity> findByMediator_Id(Long mediatorId);
}
