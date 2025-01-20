package ru.fast.bills.processing.exception;

import lombok.Getter;
import ru.fast.bills.web.dto.ErrorResponse;

import java.util.List;

@Getter
public class BillsErrorsContainer extends RuntimeException {
    private List<ErrorResponse> errors;

    public BillsErrorsContainer(List<ErrorResponse> errors) {
        this.errors = errors;
    }
}
