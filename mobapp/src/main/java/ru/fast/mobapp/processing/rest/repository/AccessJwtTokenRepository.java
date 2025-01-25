package ru.fast.mobapp.processing.rest.repository;

import java.util.Date;

public interface AccessJwtTokenRepository {

    void save(String key, String value, Date expiredAt);

    String get(String key);
}
