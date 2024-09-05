package com.vn.oder_service.repository;

import org.springframework.data.repository.CrudRepository;

import com.vn.oder_service.entity.redis.Order;

public interface IOrderRedisRepository extends CrudRepository<Order, String> {

}
