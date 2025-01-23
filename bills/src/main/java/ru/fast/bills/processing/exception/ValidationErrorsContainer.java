package ru.fast.bills.processing.exception;

import lombok.Getter;

import java.util.List;

@Getter
public class ValidationErrorsContainer extends RuntimeException {
    private List<ValidationError> errors;

    public ValidationErrorsContainer(List<ValidationError> errors) {
        this.errors = errors;
    }
}
