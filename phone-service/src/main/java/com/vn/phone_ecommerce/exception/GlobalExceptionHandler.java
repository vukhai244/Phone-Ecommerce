package com.vn.phone_ecommerce.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.github.andrewoma.dexx.collection.HashMap;
import com.github.andrewoma.dexx.collection.Map;
import com.vn.phone_ecommerce.dto.response.ApiResponse;

import java.util.Date;
import java.util.stream.Collectors;

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

        @ExceptionHandler(value = CategoryException.class)
        public ResponseEntity<ApiResponse> handleCategoryException(CategoryException e) {
                ErrorCode errorCode = e.getErrorCode();

                ApiResponse apiResponse = ApiResponse.builder()
                                .code(errorCode.getCode())
                                .message(errorCode.getMessage())
                                .build();

                return ResponseEntity
                                .status(errorCode.getStatusCode())
                                .body(apiResponse);
        }

        @ExceptionHandler(value = PhoneException.class)
        public ResponseEntity<ApiResponse> handlePhoneException(PhoneException e) {
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
