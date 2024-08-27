package com.vn.phone_ecommerce.repository;

import com.vn.phone_ecommerce.entity.Categories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICategoriesRepository extends JpaRepository<Categories, Integer> {
}
