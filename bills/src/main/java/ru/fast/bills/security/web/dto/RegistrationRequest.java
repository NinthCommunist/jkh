package ru.fast.bills.security.web.dto;

import ru.fast.bills.security.data.models.MediatorAuthority;

import java.util.List;

public record RegistrationRequest(String username, String password, List<MediatorAuthority> roles) {
}
