package com.vn.phone_ecommerce.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@Embeddable
public class PhoneCategoryId implements Serializable {

    @Column(name = "phone_id", length = 36)
    private String phoneId;

    @Column(name = "category_id", length = 36)
    private String categoryId;

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        PhoneCategoryId that = (PhoneCategoryId) o;
        return Objects.equals(phoneId, that.phoneId) &&
                Objects.equals(categoryId, that.categoryId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(phoneId, categoryId);
    }
}
