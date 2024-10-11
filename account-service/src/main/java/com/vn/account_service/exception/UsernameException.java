package com.vn.account_service.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsernameException extends RuntimeException {

    private ErrorCode errorCode;

    public UsernameException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
