package ru.fast.bills.processing.exception;

import lombok.Getter;

@Getter
public abstract class BillsAbstractException extends RuntimeException {

    private final String field;
    private final String code;
    private final Object[] params;

    protected BillsAbstractException(String field, String code, Object[] params) {
        this.field = field;
        this.code = code;
        this.params = params;
    }

    protected BillsAbstractException(String field, String code) {
        this.field = field;
        this.code = code;
        this.params = new Object[]{};
    }

}
