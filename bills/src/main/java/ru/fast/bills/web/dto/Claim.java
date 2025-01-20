package ru.fast.bills.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.UUID;

/**
 * DTO for {@link ru.fast.bills.data.models.ClaimEntity}
 */
public record Claim(UUID id, @Size(max = 50) @NotBlank String title,
                    @NotBlank String definition, Address address,
                    User user, Executor executor) {
}