package com.vn.phone_ecommerce.entity;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "phone_category")
@Getter
@Setter
@NoArgsConstructor
public class PhoneCategory {
    @EmbeddedId
    private PhoneCategoryId id;

    @ManyToOne
    @MapsId("phoneId")
    @JoinColumn(name = "phone_id")
    private Phone phone;

    @ManyToOne
    @MapsId("categoryId")
    @JoinColumn(name = "category_id")
    private Category category;

}
