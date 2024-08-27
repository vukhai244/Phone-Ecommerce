package com.vn.phone_ecommerce.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "phone", indexes = {
        @Index(name = "idx_product_code", columnList = "phone_code"),
        @Index(name = "idx_category", columnList = "category_id")
})
@NoArgsConstructor
@Getter
@Setter
public class Phone {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "phone_id")
    private Long id;

    @Column(name = "phone_code", length = 50, nullable = false, unique = true)
    private String code;

    @Column(name = "phone_name", nullable = false)
    private String name;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "price", nullable = false)
    private int price;

    @Column(name = "brand", length = 100)
    private String brand;

    @Column(name = "model", length = 100)
    private String model;

    @Column(name = "stock_quantity", nullable = false)
    private int stockQuantity;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "phone_category", joinColumns = @JoinColumn(name = "phone_id"), inverseJoinColumns = @JoinColumn(name = "category_id"))
    private Set<Categories> categories = new HashSet<>();

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

}
