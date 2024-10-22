package com.vn.cart_service.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PhoneDTO {
    private String code;
    private String name;
    private String imageUrls;
    private String description;
    private int price;
    private String brand;
    private String model;

    @Override
    public String toString() {
        return "PhoneDTO{" +
                ", name='" + code + '\'' +
                ", name='" + name + '\'' +
                ", name='" + imageUrls + '\'' +
                ", name='" + description + '\'' +
                ", name='" + price + '\'' +
                ", name='" + brand + '\'' +
                ", price=" + model +
                '}';
    }
}
