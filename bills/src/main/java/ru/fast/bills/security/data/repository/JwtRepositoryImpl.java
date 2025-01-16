package ru.fast.bills.security.data.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.time.Duration;
import java.util.Objects;

@Repository
@RequiredArgsConstructor
public class JwtRepositoryImpl implements JwtRepository {

    private final RedisTemplate<String, String> jwtRedisTemplate;
    @Value("${app.security.jwt.access.expiration}")
    private Duration accessExpiration;

    @Override
    public boolean tokenExist(String authenticationName, String jwt) {
        String jwtValue = this.jwtRedisTemplate.opsForValue().get(authenticationName);
        return Objects.equals(jwtValue, jwt);
    }

    @Override
    public void saveToken(String authenticationName, String jwt) {
        this.jwtRedisTemplate.opsForValue().set(authenticationName, jwt, accessExpiration);
    }

    @Override
    public void remove(String authenticationName) {
        this.jwtRedisTemplate.opsForValue().getAndDelete(authenticationName);
    }
}
