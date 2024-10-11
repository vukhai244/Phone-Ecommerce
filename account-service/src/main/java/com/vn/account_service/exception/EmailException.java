package com.vn.account_service.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmailException extends RuntimeException {

    private ErrorCode errorCode;

    public EmailException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
