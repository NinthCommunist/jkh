package ru.fast.mobapp.processing.rest.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import ru.fast.mobapp.processing.rest.dto.Credentials;
import ru.fast.mobapp.processing.rest.dto.Tokens;

import java.util.Map;

@FeignClient(name = "auth", url = "http://localhost:8081/api/v1/bills/auth")
public interface AuthClient {

    @PostMapping("/login")
    ResponseEntity<Tokens> login(Credentials credentials);

    @PostMapping("/refresh")
    ResponseEntity<Tokens> refresh(Map<String, String> refreshToken);
}
