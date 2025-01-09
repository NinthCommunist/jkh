package ru.fast.bills.processing.exception;

public class ExecutorException extends BillsAbstractException {

    private ExecutorException(String field, String code, Object[] params) {
        super(field, code, params);
    }

    private ExecutorException(String field, String code) {
        super(field, code);
    }


    public static ExecutorException executorNotFound(long id) {
        return new ExecutorException("id", "bills.errors.not_found", new Object[]{"executor", id});
    }
}
