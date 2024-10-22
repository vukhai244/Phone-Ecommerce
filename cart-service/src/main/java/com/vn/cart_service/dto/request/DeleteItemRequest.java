package com.vn.cart_service.dto.request;

import lombok.Getter;

@Getter
public class DeleteItemRequest {
    private String userId;
    private String phoneId;

}
