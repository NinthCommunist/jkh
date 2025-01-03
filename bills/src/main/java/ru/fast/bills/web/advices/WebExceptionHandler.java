package ru.fast.bills.web.advices;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@RestControllerAdvice
public class WebExceptionHandler {


    @ExceptionHandler(ConstraintViolationException.class)
    public ProblemDetail jakartaExceptionHandler(ConstraintViolationException ex) {
        Map<String, String> violations = ex.getConstraintViolations().stream()
                .collect(Collectors.toMap(
                        violation -> violation.getPropertyPath().toString(),
                        ConstraintViolation::getMessage,
                        (oldValue, newValue) -> oldValue
                ));
        return ProblemDetail.forStatusAndDetail(BAD_REQUEST, violations.toString());
    }
}
