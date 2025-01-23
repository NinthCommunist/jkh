package ru.fast.bills.processing.exception;

public record ValidationError(String field, String code, Object[] params) {

    public static ValidationError of(String field, String code, Object[] params) {
        return new ValidationError(field, code, params);
    }

    public static ValidationError of(String field, String code) {
        return new ValidationError(field, code, new Object[0]);
    }
}