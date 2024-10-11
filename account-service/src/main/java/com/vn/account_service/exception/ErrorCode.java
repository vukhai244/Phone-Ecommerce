package com.vn.account_service.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    DATABASE_EXCEPTION(1001, "User existed"),
    USER_EXISTED(1002, "User existed"),
    USER_NOT_EXISTED(1003, "User not existed"),
    EMAIL_EXISTED(1004, "Email existed"),
    ACCOUNT_NOT_FOUND(1005, "Account not found"),
    UNAUTHENTICATID(1006, "Unauthenticated");

    private int code;
    private String message;
}
