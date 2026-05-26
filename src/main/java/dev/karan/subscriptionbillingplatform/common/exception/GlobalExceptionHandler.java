package dev.karan.subscriptionbillingplatform.common.exception;

import dev.karan.subscriptionbillingplatform.common.response.ApiResponse;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger =
            LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<ApiResponse<?>> handleDuplicateEmailException(
            EmailAlreadyExistsException exception){

        ApiResponse<?> response = new ApiResponse<>(
                false,
                exception.getMessage(),
                null,
                LocalDateTime.now());

        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<ApiResponse<?>> handleInvalidCredentialException(
            InvalidCredentialsException exception){

        ApiResponse<?> response = new ApiResponse<>(
                false,
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
                exception.getBindingResult().getFieldErrors(); //Because when validation fails, Spring stores all validation errors in a Map

        Map<String, String> errors = new HashMap<>();

        for(FieldError error : fieldErrors){
            errors.put(error.getField(), error.getDefaultMessage());
        }

        ApiResponse<?> response = new ApiResponse<>(
                false,
                "Validation failed",
                errors,
                LocalDateTime.now());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<?>> handleGenericException(Exception ex){

        logger.error("Unexpected exception occured", ex);

        ApiResponse<?> response = new ApiResponse<>(
                false,
            "Something went wrong. Please try again later.",
            null,
            LocalDateTime.now());

    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(response);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ApiResponse<?>> handleUserNotFoumdException(UserNotFoundException ex){

        ApiResponse<?> response = new ApiResponse<>(false,
                ex.getMessage(),
                null,
                LocalDateTime.now());

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(response);
    }

    @ExceptionHandler(InvalidRefreshTokenException.class)
    public ResponseEntity<ApiResponse<?>> handleInvalidRefreshTokenException(InvalidRefreshTokenException ex){

        ApiResponse<?> response = new ApiResponse<>(false
                ,ex.getMessage(),
                null,
                LocalDateTime.now());

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(response);
    }

    @ExceptionHandler(DuplicateResourceException.class)
    public ResponseEntity<ApiResponse<?>> handleDuplicateResourceException(DuplicateResourceException ex) {

        ApiResponse<?> response = new ApiResponse<>(false,
                ex.getMessage(),
                null,
                LocalDateTime.now());

        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(response);

    }

    @ExceptionHandler(BusinessValidationException.class)
    public ResponseEntity<ApiResponse<?>> handleBusinessValidationExceptino(BusinessValidationException ex){

        ApiResponse<?> response = new ApiResponse<>(false,
                ex.getMessage(),
                null,
                LocalDateTime.now());

        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(response);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse<?>> handleresourcenotFoundException(ResourceNotFoundException ex){

        ApiResponse<?> response = new ApiResponse<>(false,
                ex.getMessage(),
                null,
                LocalDateTime.now());

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(response);
    }

    @ExceptionHandler(ObjectOptimisticLockingFailureException.class)
    public ResponseEntity<ApiResponse<?>> handleOptimisticLockingException(
            ObjectOptimisticLockingFailureException ex) {

        ApiResponse<?> response = new ApiResponse<>(
                false,
                "Resource was modified by another user. Please retry.",
                null,
                LocalDateTime.now()
        );

        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(response);
    }
}
