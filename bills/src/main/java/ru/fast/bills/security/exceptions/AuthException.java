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

    public static AuthException roleNotFound() {
        return new AuthException("roles", "bills.errors.auth.roles.not_found");
    }

    public static AuthException tokenNotValid(String token) {
        return new AuthException(token, "bills.errors.auth.token.not_valid", new Object[]{token});
    }
}
