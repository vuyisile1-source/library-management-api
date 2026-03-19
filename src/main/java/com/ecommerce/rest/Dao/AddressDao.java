package com.ecommerce.rest.Dao;

import com.ecommerce.rest.Models.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressDao extends JpaRepository<Address, Long> {
}
