package ru.fast.bills.security.web.dto;

import java.util.Date;

public record TokenResponse(String access, String refresh, Date accessExpired, Date refreshExpired) {
}
