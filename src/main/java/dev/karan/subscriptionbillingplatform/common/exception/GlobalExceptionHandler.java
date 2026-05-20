package dev.karan.subscriptionbillingplatform.common.exception;

import dev.karan.subscriptionbillingplatform.common.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<ApiResponse<?>> handleDuplicateEmailException(EmailAlreadyExistsException exception){
        ApiResponse<?> response = new ApiResponse<>(false,
                exception.getMessage(),
                null,
                LocalDateTime.now());

        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<ApiResponse<?>> handleInvalidCredentialException(InvalidCredentialsException exception){
        ApiResponse<?> response = new ApiResponse<>(false,
                exception.getMessage(),
                null,
                LocalDateTime.now());

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<?>> handleValidationException(
            MethodArgumentNotValidException exception){
        //MethodArgumentNotValidException->BindingResult->List<FieldError>
        List<FieldError> fieldErrors =
                exception.getBindingResult().getFieldErrors(); //Because when validation fails, Spring stores all validation errors in a:

        Map<String, String> errors = new HashMap<>();

        for(FieldError error : fieldErrors){
            errors.put(error.getField(), error.getDefaultMessage());
        }

        ApiResponse<?> response = new ApiResponse<>(false,
                "Validation failed",
                errors,
                LocalDateTime.now());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(response);
    }
}
