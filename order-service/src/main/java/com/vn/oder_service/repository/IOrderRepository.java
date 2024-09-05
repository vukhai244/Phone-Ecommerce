package com.vn.oder_service.repository;

import com.vn.oder_service.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IOrderRepository extends JpaRepository<Order, String> {
    Page<Order> findByStatus(String status, Pageable pageable);
}
