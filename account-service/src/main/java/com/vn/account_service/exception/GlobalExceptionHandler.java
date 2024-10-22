package com.vn.account_service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.vn.account_service.dto.response.ApiResponse;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

        @ExceptionHandler(value = Exception.class)
        public ResponseEntity<ApiResponse> handleException(Exception e) {
                ApiResponse apiResponse = ApiResponse.builder()
                                .code(ErrorCode.UNCATEGORIZED_EXCEPTION.getCode())
                                .message(ErrorCode.UNCATEGORIZED_EXCEPTION.getMessage())
                                .build();

                return ResponseEntity
                                .badRequest()
                                .body(apiResponse);
        }

        @ExceptionHandler(value = UsernameException.class)
        public ResponseEntity<ApiResponse> handleUsernameException(UsernameException e) {
                ErrorCode errorCode = e.getErrorCode();

                ApiResponse apiResponse = ApiResponse.builder()
                                .code(errorCode.getCode())
                                .message(errorCode.getMessage())
                                .build();

                return ResponseEntity
                                .status(errorCode.getStatusCode())
                                .body(apiResponse);
        }

        @ExceptionHandler(value = EmailException.class)
        public ResponseEntity<ApiResponse> handleEmailException(EmailException e) {
                ErrorCode errorCode = e.getErrorCode();

                ApiResponse apiResponse = ApiResponse.builder()
                                .code(errorCode.getCode())
                                .message(errorCode.getMessage())
                                .build();

                return ResponseEntity
                                .status(errorCode.getStatusCode())
                                .body(apiResponse);
        }

        @ExceptionHandler(value = DatabaseException.class)
        public ResponseEntity<ApiResponse> handleDatabaseException(DatabaseException e) {
                ErrorCode errorCode = e.getErrorCode();

                ApiResponse apiResponse = ApiResponse.builder()
                                .code(errorCode.getCode())
                                .message(errorCode.getMessage())
                                .build();

                return ResponseEntity
                                .status(errorCode.getStatusCode())
                                .body(apiResponse);
        }

        @ExceptionHandler(value = AccountException.class)
        public ResponseEntity<ApiResponse> handleAccountException(AccountException e) {
                ErrorCode errorCode = e.getErrorCode();

                ApiResponse apiResponse = ApiResponse.builder()
                                .code(errorCode.getCode())
                                .message(errorCode.getMessage())
                                .build();

                return ResponseEntity
                                .status(errorCode.getStatusCode())
                                .body(apiResponse);
        }

        @ExceptionHandler(value = AuthException.class)
        public ResponseEntity<ApiResponse> handleAuthException(AuthException e) {
                ErrorCode errorCode = e.getErrorCode();

                ApiResponse apiResponse = ApiResponse.builder()
                                .code(errorCode.getCode())
                                .message(errorCode.getMessage())
                                .build();

                return ResponseEntity
                                .status(errorCode.getStatusCode())
                                .body(apiResponse);
        }

        @ExceptionHandler(value = AccessDeniedException.class)
        public ResponseEntity<ApiResponse> handleAccessDeniedException(AccessDeniedException e) {
                ErrorCode errorCode = ErrorCode.UNAUTHORIZED;
                return ResponseEntity
                                .status(errorCode.getStatusCode()).body(ApiResponse.builder()
                                                .code(errorCode.getCode())
                                                .message(errorCode.getMessage())
                                                .build());

        }

        @ExceptionHandler(MethodArgumentNotValidException.class)
        public ResponseEntity<Object> handleValidationExceptions(
                        MethodArgumentNotValidException ex) {
                Map<String, String> errors = new HashMap<>();

                ex.getBindingResult().getAllErrors().forEach((error) -> {
                        String fieldName = ((FieldError) error).getField();
                        String errorMessage = error.getDefaultMessage();
                        errors.put(fieldName, errorMessage);
                });

                Map<String, Object> response = new HashMap<>();
                response.put("timestamp", new Date());
                response.put("status", HttpStatus.BAD_REQUEST.value());
                response.put("errors", errors);

                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

}
