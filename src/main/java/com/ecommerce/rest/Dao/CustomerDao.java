package com.ecommerce.rest.Dao;

import com.ecommerce.rest.Models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerDao extends JpaRepository<Customer, Long> {
    Optional<Customer> findByMobileNumber(String mobileNumber);
    Optional<Customer> findBySessionToken(String sessionToken);
}
