package com.vn.phone_ecommerce.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryException extends RuntimeException {

    private ErrorCode errorCode;

    public CategoryException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

}
