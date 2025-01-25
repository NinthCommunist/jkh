package ru.fast.mobapp.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.fast.mobapp.processing.rest.client.RestClient;
import ru.fast.mobapp.web.dto.User;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final RestClient userClient;

    public User get(UUID userId) {
        User user = this.userClient.getUser(userId);
        return user;
    }
}
