package ru.fast.mobapp.processing.rest.client;

import feign.RequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.fast.mobapp.processing.rest.client.helpers.AuthTokenSupplier;
import ru.fast.mobapp.processing.rest.client.helpers.AuthenticationRequestInterceptor;

@Configuration
public class BillsFeignConfiguration {

    @Bean
    public RequestInterceptor requestInterceptor(AuthTokenSupplier authTokenSupplier) {
        return new AuthenticationRequestInterceptor(authTokenSupplier);
    }
}
