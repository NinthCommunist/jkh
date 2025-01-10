package ru.fast.bills.processing.mappers;

import org.mapstruct.*;
import ru.fast.bills.data.models.ExecutorEntity;
import ru.fast.bills.web.dto.Executor;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public abstract class ExecutorMapper {

    public abstract ExecutorEntity toEntity(Executor executorEntityDto);

    public abstract Executor toDto(ExecutorEntity executorEntity);

    @Mapping(target = "id", ignore = true)
    public abstract void updateEntity(@MappingTarget ExecutorEntity executor, Executor newExecutor);

    public abstract List<Executor> toDtoList(List<ExecutorEntity> executorEntities);
}

