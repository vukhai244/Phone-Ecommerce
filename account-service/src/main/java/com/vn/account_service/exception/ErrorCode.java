package com.vn.account_service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    UNCATEGORIZED_EXCEPTION(9999, "Uncategorized erro", HttpStatus.INTERNAL_SERVER_ERROR),
    DATABASE_EXCEPTION(1001, "Database error", HttpStatus.INTERNAL_SERVER_ERROR),
    USER_EXISTED(1002, "User existed", HttpStatus.BAD_REQUEST),
    USER_NOT_EXISTED(1003, "User not existed", HttpStatus.NOT_FOUND),
    EMAIL_EXISTED(1004, "Email existed", HttpStatus.CONFLICT),
    ACCOUNT_NOT_EXISTED(1005, "Account not existed", HttpStatus.NOT_FOUND),
    UNAUTHENTICATID(1006, "Unauthenticated", HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED(1007, "You do not have permission", HttpStatus.FORBIDDEN),
    VALIDATION_ERROR(1008, "Validation failed", HttpStatus.BAD_REQUEST),
    ;

    private int code;
    private String message;
    private HttpStatusCode statusCode;
}
