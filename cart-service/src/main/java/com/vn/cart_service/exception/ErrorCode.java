package com.vn.cart_service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    UNCATEGORIZED_EXCEPTION(9999, "Uncategorized erro", HttpStatus.INTERNAL_SERVER_ERROR),
    DATABASE_EXCEPTION(1001, "Database error", HttpStatus.INTERNAL_SERVER_ERROR),
    PHONE_NOT_EXISTED(1002, "Phone not found", HttpStatus.NOT_FOUND),
    CART_NOT_EXISTED(1003, "Cart not found", HttpStatus.NOT_FOUND),
    USER_NOT_EXISTED(1004, "Account not existed", HttpStatus.NOT_FOUND),
    VALIDATION_ERROR(1008, "Validation failed", HttpStatus.BAD_REQUEST),;

    private int code;
    private String message;
    private HttpStatusCode statusCode;
}
