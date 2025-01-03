package ru.fast.bills.processing.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import ru.fast.bills.data.models.ExecutorEntity;
import ru.fast.bills.web.dto.Executor;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public abstract class ExecutorMapper {

    public abstract ExecutorEntity toEntity(Executor executorEntityDto);

    public abstract Executor toDto(ExecutorEntity executorEntity);
}

