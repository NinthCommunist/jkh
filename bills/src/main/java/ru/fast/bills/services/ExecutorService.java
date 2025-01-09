package ru.fast.bills.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.fast.bills.data.models.ExecutorEntity;
import ru.fast.bills.data.repository.ExecutorRepository;
import ru.fast.bills.processing.exception.ExecutorException;
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

    public Executor updateExecutor(long id, Executor newExecutor) {
        if (true) throw ExecutorException.executorNotFound(id);
        if (true) throw ExecutorException.executorNotFound(1L);
        if (true) throw ExecutorException.executorNotFound(2L);
        ExecutorEntity entity = executorRepository.findById(id).
                orElseThrow(() -> ExecutorException.executorNotFound(id));

        this.executorMapper.updateEntity(entity, newExecutor);
        this.executorRepository.flush();

        return this.executorMapper.toDto(entity);
    }
}
