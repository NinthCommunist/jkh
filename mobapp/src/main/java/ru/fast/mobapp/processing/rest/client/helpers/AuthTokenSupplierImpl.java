package ru.fast.mobapp.processing.rest.client.helpers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.fast.mobapp.processing.rest.client.AuthClient;
import ru.fast.mobapp.processing.rest.dto.Credentials;
import ru.fast.mobapp.processing.rest.dto.Tokens;
import ru.fast.mobapp.processing.rest.repository.AccessJwtTokenRepository;

import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuthTokenSupplierImpl implements AuthTokenSupplier {

    private final static String ACCESS_TOKEN_KEY = "accessTokenKey";
    private final static String REFRESH_TOKEN_KEY = "refreshTokenKey";

    private final AccessJwtTokenRepository tokenRepository;
    private final AuthClient authClient;

    @Override
    public String get() {
        String accessToken = this.tokenRepository.get(ACCESS_TOKEN_KEY);
        if (accessToken != null) {
            log.debug("Find access token for client");
            return accessToken;
        }

        String refreshToken = this.tokenRepository.get(REFRESH_TOKEN_KEY);
        if (refreshToken != null) {
            log.debug("Find refresh token for client");
            Tokens tokens = this.authClient.refresh(Map.of("refresh", refreshToken)).getBody();
            this.tokenRepository.save(ACCESS_TOKEN_KEY, tokens.access(), tokens.accessExpired());
            log.debug("Get access token from refresh");
            return tokens.access();
        }

        Tokens tokens = this.authClient.login(new Credentials("mobapp", "mobapp")).getBody();
        log.debug("Use login with credentials request");
        this.tokenRepository.save(ACCESS_TOKEN_KEY, tokens.access(), tokens.accessExpired());
        this.tokenRepository.save(REFRESH_TOKEN_KEY, tokens.refresh(), tokens.refreshExpired());
        return tokens.access();
    }
}
