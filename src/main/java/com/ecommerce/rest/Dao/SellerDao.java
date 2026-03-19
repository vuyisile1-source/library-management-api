package com.ecommerce.rest.Dao;

import com.ecommerce.rest.Models.Seller;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SellerDao extends JpaRepository<Seller, Long> {
    Optional<Seller> findByMobileNumber(String mobileNumber);
    Optional<Seller> findBySessionToken(String token);
}
