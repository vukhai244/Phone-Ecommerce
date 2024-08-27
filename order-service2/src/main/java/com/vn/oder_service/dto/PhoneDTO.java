package com.vn.oder_service.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class PhoneDTO {
    private Long id;
    private int price;
    private int stockQuantity;
}
