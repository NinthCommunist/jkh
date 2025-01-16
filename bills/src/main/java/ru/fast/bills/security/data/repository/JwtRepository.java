package ru.fast.bills.security.data.repository;

public interface JwtRepository {

    boolean tokenExist(String authenticationName, String jwt);

    void saveToken(String authenticationName, String jwt);

    void remove(String authenticationName);
}
