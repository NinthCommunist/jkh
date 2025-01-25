package ru.fast.mobapp.processing.rest.dto;

import java.util.Date;

public record Tokens(String access, String refresh, Date accessExpired, Date refreshExpired) {
}
