package ru.fast.bills.security.web.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.fast.bills.security.service.AuthenticationService;
import ru.fast.bills.security.web.dto.LoginRequest;
import ru.fast.bills.security.web.dto.RefreshTokenRequest;
import ru.fast.bills.security.web.dto.RegistrationRequest;
import ru.fast.bills.security.web.dto.TokenResponse;

import static ru.fast.bills.utils.AuthorityConstant.SUPER_ADMIN;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/registration")
    @PreAuthorize(SUPER_ADMIN)
    @ResponseStatus(HttpStatus.OK)
    public void registration(@RequestBody RegistrationRequest registr) {
        this.authenticationService.registration(registr);
    }

    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@RequestBody LoginRequest login) {
        TokenResponse tokenResponse = this.authenticationService.login(login);
        return ResponseEntity.ok(tokenResponse);
    }

    @PostMapping("/refresh")
    public ResponseEntity<TokenResponse> refreshToken(@RequestBody RefreshTokenRequest refreshTokenRequest) {
        TokenResponse token = this.authenticationService.refreshToken(refreshTokenRequest);
        return ResponseEntity.ok(token);
    }
}
