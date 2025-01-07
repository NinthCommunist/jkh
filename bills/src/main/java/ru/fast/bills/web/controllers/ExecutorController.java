package ru.fast.bills.web.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.fast.bills.services.ExecutorService;
import ru.fast.bills.web.dto.Executor;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/executors")
public class ExecutorController {

    private final ExecutorService executorService;

    @PostMapping
    public ResponseEntity<Executor> createExecutor(@RequestBody @Valid Executor executor) {
        Executor createdExecutor = this.executorService.createExecutor(executor);

        return ResponseEntity.status(HttpStatus.CREATED).body(createdExecutor);
    }

    @PutMapping(path = "{executorId:\\d+}")
    public ResponseEntity<Executor> updateExecutor(@PathVariable("executorId") long executorId,
                                                   @RequestBody @Valid Executor newExecutor) {

        Executor executor = executorService.updateExecutor(executorId, newExecutor);
        return ResponseEntity.ok(executor);

    }
}
