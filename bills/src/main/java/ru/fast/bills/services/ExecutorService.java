package ru.fast.bills.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.fast.bills.data.models.ExecutorEntity;
import ru.fast.bills.data.repository.ExecutorRepository;
import ru.fast.bills.processing.mappers.ExecutorMapper;
import ru.fast.bills.web.dto.Executor;

@Service
@RequiredArgsConstructor
public class ExecutorService {

    private final ExecutorRepository executorRepository;
    private final ExecutorMapper executorMapper;


    public Executor createExecutor(Executor executor) {
        ExecutorEntity entity = this.executorMapper.toEntity(executor);

        ExecutorEntity executorEntity = this.executorRepository.saveAndFlush(entity);
        return this.executorMapper.toDto(executorEntity);
    }
}
