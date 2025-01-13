package ru.fast.bills.security.exceptions;

import ru.fast.bills.processing.exception.BillsAbstractException;

public class AuthException extends BillsAbstractException {

    private AuthException(String field, String code, Object[] params) {
        super(field, code, params);
    }

    private AuthException(String field, String code) {
        super(field, code);
    }


    public static AuthException mediatorAlreadyRegistered(String serviceName) {
        return new AuthException("serviceName", "bills.errors.auth.mediator.registered", new Object[]{serviceName});
    }

    public static AuthException roleListIsEmpty() {
        return new AuthException("roles", "bills.errors.auth.roles.list_isEmpty");
    }

    public static AuthException loginError() {
        return new AuthException("login", "bills.errors.auth.mediator.login_incorrect");
    }
}
