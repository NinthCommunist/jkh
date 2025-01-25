package ru.fast.mobapp.processing.rest.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.fast.mobapp.web.dto.User;

import java.util.UUID;

@FeignClient(name = "bills", path = "/users", configuration = BillsFeignConfiguration.class)
@Component
public interface RestClient {

    @GetMapping("/{userId}")
    User getUser(@PathVariable UUID userId);
}
