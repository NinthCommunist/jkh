package ru.fast.bills.web.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.fast.bills.services.ExecutorService;
import ru.fast.bills.web.dto.Executor;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/executors")
public class ExecutorController {

    private final ExecutorService executorService;

    @PostMapping
    public ResponseEntity<Executor> createExecutor(Executor executor) {
        Executor createdExecutor = this.executorService.createExecutor(executor);

        return ResponseEntity.status(HttpStatus.CREATED).body(createdExecutor);
    }
}
