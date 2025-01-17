package ru.fast.bills.processing.exception;

import java.util.UUID;

public class UserException extends BillsAbstractException {

    private UserException(String field, String code, Object[] params) {
        super(field, code, params);
    }

    private UserException(String field, String code) {
        super(field, code);
    }


    public static UserException userNotFound(UUID id) {
        return new UserException("id", "bills.errors.not_found", new Object[]{"user", id});
    }
}
