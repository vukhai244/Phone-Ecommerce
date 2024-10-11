package com.vn.account_service.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountException extends RuntimeException {

    private ErrorCode errorCode;

    public AccountException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
