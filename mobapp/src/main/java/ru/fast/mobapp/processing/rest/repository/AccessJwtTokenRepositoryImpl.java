package ru.fast.mobapp.processing.rest.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class AccessJwtTokenRepositoryImpl implements AccessJwtTokenRepository {

    private final RedisTemplate<String, String> stringRedisTemplate;

    @Override
    public void save(String key, String value, Date expiredAt) {
        Duration removeAfter = Duration.between(new Date().toInstant(), expiredAt.toInstant());
        this.stringRedisTemplate.opsForValue().set(key, value, removeAfter.minus(Duration.ofMinutes(1)));
    }

    @Override
    public String get(String key) {
        return this.stringRedisTemplate.opsForValue().get(key);
    }
}
