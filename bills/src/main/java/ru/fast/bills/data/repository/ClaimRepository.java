package ru.fast.bills.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.fast.bills.data.models.ClaimEntity;

import java.util.List;
import java.util.UUID;

@Repository
public interface ClaimRepository extends JpaRepository<ClaimEntity, UUID> {

    @Override
    @Query(value = "from ClaimEntity ce join fetch ce.user join fetch ce.executor")
    List<ClaimEntity> findAll();

    @Query(value = "from ClaimEntity ce where ce.user.id= :userId")
    List<ClaimEntity> findLazyAllByUser_Id(UUID userId);

}
