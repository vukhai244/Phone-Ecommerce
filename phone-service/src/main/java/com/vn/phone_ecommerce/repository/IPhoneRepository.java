package com.vn.phone_ecommerce.repository;

import com.vn.phone_ecommerce.entity.Phone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IPhoneRepository extends JpaRepository<Phone, Long> {
    @Query("FROM Phone WHERE name LIKE CONCAT('%', ?1, '%')")
    List<Phone> findAllByName(String name);
}
