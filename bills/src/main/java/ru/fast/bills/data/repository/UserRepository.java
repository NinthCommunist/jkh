package ru.fast.bills.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.fast.bills.data.models.UserEntity;

import java.util.UUID;

public interface UserRepository extends JpaRepository<UserEntity, UUID> {

}
