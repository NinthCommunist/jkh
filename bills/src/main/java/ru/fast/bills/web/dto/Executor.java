package ru.fast.bills.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record Executor(Long id, @Size(min = 0, max = 10) String phone,
                       @NotBlank
                       @Size(min = 3) String name,
                       String organization) {
}
