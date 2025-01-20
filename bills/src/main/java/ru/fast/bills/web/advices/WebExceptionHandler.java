package ru.fast.bills.web.advices;

import io.jsonwebtoken.JwtException;
import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.internal.engine.ConstraintViolationImpl;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.fast.bills.processing.exception.BillsAbstractException;
import ru.fast.bills.processing.exception.BillsErrorsContainer;
import ru.fast.bills.web.dto.ErrorResponse;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

@RestControllerAdvice
@RequiredArgsConstructor
@Slf4j
public class WebExceptionHandler {

    private final MessageSource messageSource;

    @ExceptionHandler(BindException.class)
    public ResponseEntity<List<ErrorResponse>> bindExceptionHandler(BindException ex) {
        log.info("Bind exception ", ex.getMessage());

        List<ErrorResponse> errorResponseList = this.mapToErrorResponse(ex.getAllErrors());

        return ResponseEntity.badRequest()
                .body(errorResponseList);
    }

    @ExceptionHandler(BillsAbstractException.class)
    public ResponseEntity<List<ErrorResponse>> billsAbstractExceptionHandler(Locale locale, BillsAbstractException ex) {
        log.info("bills exception " + ex.getField());

        String message = this.messageSource.getMessage(ex.getCode(), ex.getParams(), locale);
        ErrorResponse errorResponse = new ErrorResponse(ex.getField(), message);

        return ResponseEntity.badRequest()
                .body(Collections.singletonList(errorResponse));
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<List<ErrorResponse>> bindExceptionHandler(ConstraintViolationException ex) {
        log.info("Constraint violation exception " + ex.getMessage());

        List<ErrorResponse> errorResponseList = this.mapToErrorResponse(ex.getConstraintViolations());

        return ResponseEntity.badRequest()
                .body(errorResponseList);
    }

    @ExceptionHandler(JwtException.class)
    public ResponseEntity<List<ErrorResponse>> parseJwtException(JwtException ex) {
        log.info("Jwt parse exception " + ex.getMessage());

        ErrorResponse errorResponse = new ErrorResponse("Jwt authentication token", ex.getMessage());

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(Collections.singletonList(errorResponse));
    }

    @ExceptionHandler(BillsErrorsContainer.class)
    public ResponseEntity<List<ErrorResponse>> billsErrorContainer(BillsErrorsContainer ex) {
        log.info("Handle error container");

        List<ErrorResponse> errorResponseList = ex.getErrors();

        return ResponseEntity.badRequest()
                .body(errorResponseList);
    }


    private List<ErrorResponse> mapToErrorResponse(Collection<?> errors) {
        return errors.stream().map(er -> {
            if (er instanceof FieldError fe)
                return new ErrorResponse(fe.getField(), fe.getDefaultMessage());
            if (er instanceof ConstraintViolationImpl cvi)
                return new ErrorResponse(cvi.getPropertyPath().toString(), cvi.getMessage());
            return new ErrorResponse(null, "Произошла ошибка, проверьте корректность запроса");
        }).toList();
    }
}
