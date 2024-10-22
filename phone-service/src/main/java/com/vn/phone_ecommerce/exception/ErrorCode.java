package com.vn.phone_ecommerce.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    UNCATEGORIZED_EXCEPTION(9999, "Uncategorized erro", HttpStatus.INTERNAL_SERVER_ERROR),
    DATABASE_EXCEPTION(1001, "Database error", HttpStatus.INTERNAL_SERVER_ERROR),
    CATEGORY_EXISTED(1002, "Category not found", HttpStatus.BAD_REQUEST),
    CATEGORY_NOT_EXISTED(1003, "Category not found", HttpStatus.NOT_FOUND),
    PHONE_EXISTED(1004, "Phone existed", HttpStatus.BAD_REQUEST),
    PHONE_NOT_EXISTED(1005, "Phone not found", HttpStatus.NOT_FOUND),
    VALIDATION_ERROR(1008, "Validation failed", HttpStatus.BAD_REQUEST),;

    private int code;
    private String message;
    private HttpStatusCode statusCode;
}
