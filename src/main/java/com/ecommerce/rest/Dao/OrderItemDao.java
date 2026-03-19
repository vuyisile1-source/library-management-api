package com.ecommerce.rest.Dao;

import com.ecommerce.rest.Models.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemDao extends JpaRepository<OrderItem, Long> {
}
