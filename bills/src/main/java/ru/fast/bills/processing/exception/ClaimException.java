package ru.fast.bills.processing.exception;

import java.util.UUID;

public class ClaimException extends BillsAbstractException {
    protected ClaimException(String field, String code, Object[] params) {
        super(field, code, params);
    }

    protected ClaimException(String field, String code) {
        super(field, code);
    }

    public static ClaimException claimNotFound(UUID id) {
        return new ClaimException("id", "bills.errors.not_found", new Object[]{"claim", id});
    }
}
