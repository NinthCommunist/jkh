package ru.fast.bills.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.fast.bills.data.models.ExecutorEntity;
import ru.fast.bills.data.repository.ExecutorRepository;
import ru.fast.bills.processing.exception.ExecutorException;
import ru.fast.bills.processing.mappers.ExecutorMapper;
import ru.fast.bills.web.dto.Executor;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ExecutorService {

    private final ExecutorRepository executorRepository;
    private final ExecutorMapper executorMapper;
    private final ObjectMapper objectMapper;


    public Executor createExecutor(Executor executor) {
        ExecutorEntity entity = this.executorMapper.toEntity(executor);

        ExecutorEntity executorEntity = this.executorRepository.save(entity);
        return this.executorMapper.toDto(executorEntity);
    }

    @Transactional
    public Executor updateExecutor(long id, Executor newExecutor) {
        ExecutorEntity entity = this.findExecutorEntity(id);

        this.executorMapper.updateEntity(entity, newExecutor);
        return this.executorMapper.toDto(entity);
    }

    public ExecutorEntity findExecutorEntity(long id) {
        return this.executorRepository.findById(id)
                .orElseThrow(() -> ExecutorException.executorNotFound(id));
    }

    public List<Executor> getExecutors() {
        return this.executorMapper.toDtoList(this.executorRepository.findAll());
    }

    @Transactional
    public Executor patchExecutor(long id, JsonPatch patch) {
        ExecutorEntity executorEntity = this.findExecutorEntity(id);

        Executor executor = this.executorMapper.toDto(executorEntity);
        executor = this.applyPatchToCustomer(patch, executor);

        this.executorMapper.updateEntity(executorEntity, executor);
        return this.executorMapper.toDto(executorEntity);
    }

    public void deleteExecutor(long executorId) {
        this.executorRepository.deleteById(executorId);
    }

    @SneakyThrows
    private Executor applyPatchToCustomer(
            JsonPatch patch, Executor targetCustomer) {
        JsonNode patched = patch.apply(this.objectMapper.convertValue(targetCustomer, JsonNode.class));
        return this.objectMapper.treeToValue(patched, Executor.class);
    }

}
