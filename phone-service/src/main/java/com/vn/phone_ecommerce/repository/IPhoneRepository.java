package com.vn.phone_ecommerce.repository;

import com.vn.phone_ecommerce.entity.Phone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IPhoneRepository extends JpaRepository<Phone, String> {
    @Query("FROM Phone WHERE name LIKE CONCAT('%', ?1, '%')")
    List<Phone> findAllByName(String name);

    Optional<Phone> findByCode(String code);
}
