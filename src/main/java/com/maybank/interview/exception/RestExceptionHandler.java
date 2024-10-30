package com.maybank.interview.exception;


import com.maybank.interview.web.generated.model.RequestError;
import feign.FeignException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.core.convert.ConversionFailedException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

/**
 * Rest exception handlers
 */
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
    private static final Logger LOG = LogManager.getLogger(RestExceptionHandler.class);

    protected ResponseEntity<Object> handleRestBaseException(RestBaseException e, WebRequest request) {
        LOG.error("Handling RestBaseException :  ", e);
        return ResponseEntity.badRequest().body(buildError(BAD_REQUEST, e.getMessage(), request));
    }

    @ExceptionHandler(ConstraintViolationException.class)
    protected ResponseEntity<Object> handleConstraintViolationException(
            ConstraintViolationException e,
            WebRequest request) {
        LOG.error("Handling ConstraintViolationException : {} ", e.getMessage());

        String message = e.getConstraintViolations().size() > 1 ? e.getConstraintViolations().stream()
                .map(ConstraintViolation::getMessage)
                .sorted()
                .collect(Collectors.joining("; ")) : e.getMessage();

        return ResponseEntity.badRequest().body(buildError(BAD_REQUEST, message, request));
    }

    @ExceptionHandler(RequestException.class)
    protected final ResponseEntity<RequestError> handleRequestException(
            RequestException ex,
            WebRequest request) {
        LOG.error("Hit Request Exception : {}", ex.getRequestError().getMessage());
        return ResponseEntity.badRequest().body(buildError(BAD_REQUEST, ex.getRequestError().getMessage(), request));
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<Object> handleGenericError(Exception e, WebRequest request) {
        LOG.error("Hit General Exception : ", e);
        return ResponseEntity.internalServerError().body(buildError(INTERNAL_SERVER_ERROR, e.getMessage(), request));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    protected ResponseEntity<Object> handleIllegalArgumentExceptionError(Exception e, WebRequest request) {
        LOG.error("Hit IllegalArgumentException : ", e);
        return ResponseEntity.badRequest().body(buildError(BAD_REQUEST, e.getMessage(), request));
    }

    @ExceptionHandler(FeignException.class)
    protected ResponseEntity<Object> handleFeignException(Exception e, WebRequest request) {
        LOG.error("Hit FeignException when performing request to client endpoint : ", e);
        return ResponseEntity.badRequest().body(buildError(BAD_REQUEST, e.getMessage(), request));
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(
            Exception ex,
            @Nullable Object body,
            HttpHeaders headers,
            HttpStatusCode statusCode,
            WebRequest request) {
        LOG.debug("Generating response for exception ", ex);

        if (ex.getCause() instanceof ConversionFailedException e && e.getCause() instanceof RestBaseException re) {
            return handleRestBaseException(re, request);
        }

        super.handleExceptionInternal(ex, body, headers, statusCode, request);

        return ResponseEntity.status(statusCode)
                .headers(headers)
                .body(buildError(HttpStatus.resolve(statusCode.value()), ex.getMessage(), request));
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request) {

        // Log the error message
        LOG.error("Handling MethodArgumentNotValidException: {}", ex.getMessage());

        // Collect validation errors
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(fieldError -> {
            errors.put(fieldError.getField(), fieldError.getDefaultMessage());
        });

        // Create a custom error message
        String errorMessage = errors.isEmpty()
                ? "Validation failed"
                : "Validation errors: " + errors;

        // Build the error response
        return ResponseEntity.status(status)
                .headers(headers)
                .body(buildError(Objects.requireNonNull(HttpStatus.resolve(status.value())), errorMessage, request));
    }

    /**
     * Implement this method to build the expected error object from provided info
     *
     * @param code    HttpStatus
     * @param message error message
     * @param request WebRequest
     * @return Tokenization errorS
     */
    protected RequestError buildError(HttpStatus code, String message, WebRequest request) {
        return new RequestError()
                .timestamp(LocalDateTime.now().toString())
                .status(code.value())
                .error(code.getReasonPhrase())
                .message(message)
                .path(((ServletWebRequest) request).getRequest().getRequestURI());
    }
}