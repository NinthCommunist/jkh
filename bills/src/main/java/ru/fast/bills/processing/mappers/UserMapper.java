package ru.fast.bills.processing.mappers;

import org.mapstruct.*;
import ru.fast.bills.data.models.UserEntity;
import ru.fast.bills.web.dto.User;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public abstract class UserMapper {
    public abstract UserEntity toEntity(User userEntity);

    public abstract User toDto(UserEntity userEntity);

    @Mapping(target = "id", ignore = true)
    public abstract void updateEntity(@MappingTarget UserEntity userEntity, User newUser);

    public abstract List<User> toDtoList(List<UserEntity> executorEntities);
}