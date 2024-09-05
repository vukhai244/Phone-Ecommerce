package com.vn.cart_service.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "cart_item")
@NoArgsConstructor
@Getter
@Setter
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "cart_item_id")
    private String id;

    @ManyToOne
    @JoinColumn(name = "cart_id")
    private Cart cartId;

    @Column(name = "phone_id")
    private String phoneId;

    @Column(name = "quantity")
    private int quantity;

}
