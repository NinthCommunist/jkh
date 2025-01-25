package ru.fast.mobapp.processing.rest.client.helpers;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import static org.springframework.cloud.openfeign.security.OAuth2AccessTokenInterceptor.BEARER;

@Component
@RequiredArgsConstructor
public class AuthenticationRequestInterceptor implements RequestInterceptor {

    private final AuthTokenSupplier tokenSupplier;

    @Override
    public void apply(RequestTemplate requestTemplate) {
        String accessToken = this.tokenSupplier.get();
        requestTemplate.header(HttpHeaders.AUTHORIZATION, BEARER + " " + accessToken);
    }
}
