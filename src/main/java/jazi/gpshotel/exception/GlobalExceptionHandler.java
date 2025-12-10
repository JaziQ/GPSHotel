package jazi.gpshotel.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleResourceNotFound(ResourceNotFoundException ex) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", HttpStatus.NOT_FOUND.value());
        body.put("error", "Not Found");
        body.put("message", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGenericException(Exception ex) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
        body.put("error", "Internal Server Error");
        body.put("message", ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationExceptions(
            MethodArgumentNotValidException ex,
            WebRequest request) {

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", HttpStatus.BAD_REQUEST.value());
        body.put("error", "Validation Failed");
        body.put("path", request.getDescription(false).replace("uri=", ""));

        List<Map<String, Object>> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(this::fieldErrorToMap)
                .collect(Collectors.toList());

        body.put("validationErrors", errors);

        if (!ex.getBindingResult().getGlobalErrors().isEmpty()) {
            List<String> globalErrors = ex.getBindingResult()
                    .getGlobalErrors()
                    .stream()
                    .map(err -> err.getObjectName() + ": " + err.getDefaultMessage())
                    .collect(Collectors.toList());
            body.put("globalErrors", globalErrors);
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    private Map<String, Object> fieldErrorToMap(FieldError fieldError) {
        Map<String, Object> errorMap = new LinkedHashMap<>();
        errorMap.put("field", fieldError.getField());
        errorMap.put("message", fieldError.getDefaultMessage());
        errorMap.put("rejectedValue", fieldError.getRejectedValue());
        errorMap.put("code", fieldError.getCode());
        return errorMap;
    }
}
