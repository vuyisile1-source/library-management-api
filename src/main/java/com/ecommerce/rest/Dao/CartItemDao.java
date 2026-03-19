package com.ecommerce.rest.Dao;

import com.ecommerce.rest.Models.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemDao extends JpaRepository<CartItem, Long> {
}
