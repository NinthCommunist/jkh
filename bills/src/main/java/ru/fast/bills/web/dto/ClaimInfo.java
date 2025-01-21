package ru.fast.bills.web.dto;

import java.util.UUID;

public record ClaimInfo(UUID id, String title, Address address) {
}
