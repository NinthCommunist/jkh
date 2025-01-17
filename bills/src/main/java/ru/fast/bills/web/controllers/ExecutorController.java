package ru.fast.bills.web.controllers;

import com.github.fge.jsonpatch.JsonPatch;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.fast.bills.services.ExecutorService;
import ru.fast.bills.web.dto.Executor;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/executors")
public class ExecutorController {

    private final ExecutorService executorService;

    @GetMapping
    public ResponseEntity<List<Executor>> getExecutorList() {
        return ResponseEntity.ok(executorService.getExecutors());
    }

    @PostMapping
    public ResponseEntity<Executor> createExecutor(@RequestBody @Valid Executor executor) {
        Executor createdExecutor = this.executorService.createExecutor(executor);

        return ResponseEntity.status(HttpStatus.CREATED).body(createdExecutor);
    }

    @PutMapping(path = "{executorId:\\d+}")
    public ResponseEntity<Executor> updateExecutor(@PathVariable("executorId") long executorId,
                                                   @RequestBody @Valid Executor newExecutor) {

        Executor executor = this.executorService.updateExecutor(executorId, newExecutor);
        return ResponseEntity.ok(executor);
    }

    @PatchMapping(path = "{executorId:\\d+}")
    public ResponseEntity<Executor> patchExecutor(@PathVariable("executorId") long executorId,
                                                  @RequestBody JsonPatch patch) {
        Executor patchedExecutor = this.executorService.patchExecutor(executorId, patch);
        return ResponseEntity.ok(patchedExecutor);
    }

    @DeleteMapping(path = "{executorId:\\d+}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteExecutor(@PathVariable("executorId") long executorId) {
        this.executorService.deleteExecutor(executorId);
    }
}
