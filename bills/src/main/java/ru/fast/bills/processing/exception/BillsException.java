package ru.fast.bills.processing.exception;

import lombok.Getter;

@Getter
public class BillsException extends RuntimeException {

    private final Object[] params;

    private BillsException(String message, Object[] params) {
        super(message);
        this.params = params;
    }

    public static BillsException throwException(String message, Object[] params) {
        return new BillsException(message, params);
    }
}
