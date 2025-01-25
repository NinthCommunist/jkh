package ru.fast.mobapp.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record User(UUID id, @Size(min = 0, max = 12) String phone,
                   @Size(min = 3) @NotBlank String nickname,
                   String firstName, String lastName, String mail) {
}
