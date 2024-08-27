package com.vn.cart_service.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PhoneDTO {
    private Long id;
    private String code;
    private String name;
    private String description;
    private int price;
    private String brand;
    private String model;
}
