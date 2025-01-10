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

        ExecutorEntity executorEntity = this.executorRepository.saveAndFlush(entity);
        return this.executorMapper.toDto(executorEntity);
    }

    @Transactional
    public Executor updateExecutor(long id, Executor newExecutor) {
        ExecutorEntity entity = executorRepository.findById(id)
                .orElseThrow(() -> ExecutorException.executorNotFound(id));

        this.executorMapper.updateEntity(entity, newExecutor);
        this.executorRepository.flush();

        return this.executorMapper.toDto(entity);
    }

    public List<Executor> getExecutors() {
        return this.executorMapper.toDtoList(executorRepository.findAll());
    }

    @Transactional
    public Executor patchExecutor(long id, JsonPatch patch) {
        Executor executor = findExtractorOrElseThrow(id);
        executor = applyPatchToCustomer(patch, executor);

        executorRepository.saveAndFlush(executorMapper.toEntity(executor));
        return executor;
    }

    public void deleteExecutor(long executorId) {
        executorRepository.deleteById(executorId);
        executorRepository.flush();
    }

    private Executor findExtractorOrElseThrow(long id) {
        return this.executorRepository.findById(id)
                .map(executorMapper::toDto)
                .orElseThrow(() -> ExecutorException.executorNotFound(id));
    }

    @SneakyThrows
    private Executor applyPatchToCustomer(
            JsonPatch patch, Executor targetCustomer) {
        JsonNode patched = patch.apply(objectMapper.convertValue(targetCustomer, JsonNode.class));
        return objectMapper.treeToValue(patched, Executor.class);
    }

}
