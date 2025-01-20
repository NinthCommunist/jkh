package ru.fast.bills.web.dto;

/**
 * DTO for {@link ru.fast.bills.data.models.Address}
 */
public record Address(String city, String street, String house) {
}