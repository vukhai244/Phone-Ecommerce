package com.vn.phone_ecommerce.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PhoneException extends RuntimeException {

    private ErrorCode errorCode;

    public PhoneException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
