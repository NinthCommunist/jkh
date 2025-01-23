package ru.fast.bills.services;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.fast.bills.data.models.UserEntity;
import ru.fast.bills.data.repository.UserRepository;
import ru.fast.bills.processing.exception.UserException;
import ru.fast.bills.processing.mappers.UserMapper;
import ru.fast.bills.web.dto.User;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public User createUser(User user) {
        UserEntity userEntity = this.userMapper.toEntity(user);
        UserEntity savedUser = this.userRepository.save(userEntity);
        return this.userMapper.toDto(savedUser);
    }

    public List<User> getAllUsers() {
        List<UserEntity> allUsers = this.userRepository.findAll();
        return this.userMapper.toDtoList(allUsers);
    }

    @Transactional
    @CacheEvict(value = "userEntity", key = "#userId")
    public User updateUser(UUID userId, User newUser) {
        UserEntity userEntity = this.findUserEntityCacheable(userId);
        this.userMapper.updateEntity(userEntity, newUser);
        return this.userMapper.toDto(userEntity);
    }

    @Cacheable(value = "userEntity", key = "#userId")
    public UserEntity findUserEntityCacheable(UUID userId) {
        return this.userRepository.findById(userId)
                .orElseThrow(() -> UserException.userNotFound(userId));
    }

    @CacheEvict(value = "userEntity", key = "#userId")
    public void deleteUser(UUID userId) {
        this.userRepository.deleteById(userId);
    }

    public User get(UUID userId) {
        UserEntity userEntity = this.findUserEntityCacheable(userId);
        return this.userMapper.toDto(userEntity);
    }
}
