package com.vn.phone_ecommerce.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DatabaseException extends RuntimeException {

    private ErrorCode errorCode;

    public DatabaseException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
