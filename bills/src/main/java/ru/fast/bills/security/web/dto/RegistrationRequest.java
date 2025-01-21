package ru.fast.bills.security.web.dto;

import ru.fast.bills.security.data.models.MediatorAuthority;

public record RegistrationRequest(String username, String password, MediatorAuthority role) {
}
