package ru.fast.bills.web.advices;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.fast.bills.processing.exception.BillsException;

import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@RestControllerAdvice
@RequiredArgsConstructor
public class WebExceptionHandler {


    private final MessageSource messageSource;


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

    @ExceptionHandler(BillsException.class)
    public ResponseEntity<ProblemDetail> handleBindException(Locale locale, BillsException ex) {
        ProblemDetail problemDetail = ProblemDetail
                .forStatusAndDetail(HttpStatus.BAD_REQUEST,
                        this.messageSource.getMessage(ex.getMessage(), ex.getParams(),
                                ex.getMessage(), locale));

        return ResponseEntity.badRequest()
                .body(problemDetail);
    }
}
