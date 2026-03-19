package com.ecommerce.rest.Services;

import com.ecommerce.rest.Dao.CustomerDao;
import com.ecommerce.rest.Models.Customer;
import com.ecommerce.rest.Models.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class CustomerService {

    @Autowired
    private CustomerDao customerDao;

    public Customer getCurrentCustomer(String token) {
        return customerDao.findBySessionToken(token)
                .orElseThrow(() -> new RuntimeException("Invalid or expired session token"));
    }

    public List<Order> getCustomerOrders(String token) {
        Customer customer = getCurrentCustomer(token);
        return customer.getOrders();
    }

    public List<Customer> getAllCustomers() {
        return customerDao.findAll();
    }

    public void updateCustomer(String token, Customer updatedCustomer) {
        Customer customer = getCurrentCustomer(token);
        if (updatedCustomer.getName() != null) {
            customer.setName(updatedCustomer.getName());
        }
        if (updatedCustomer.getEmail() != null) {
            customer.setEmail(updatedCustomer.getEmail());
        }
        if (updatedCustomer.getMobileNumber() != null) {
            customer.setMobileNumber(updatedCustomer.getMobileNumber());
        }

        customerDao.saveAndFlush(customer);
    }

    public void updateCredentials(String token, Customer updatedCustomer) {
        Customer customer = getCurrentCustomer(token);
        if (updatedCustomer.getEmail() != null) {
            customer.setName(updatedCustomer.getEmail());
        }
        if (updatedCustomer.getPassword() != null) {
            customer.setEmail(updatedCustomer.getPassword());
        }
        customerDao.saveAndFlush(customer);
    }

    public void updatePassword(String token, String password) {
        Customer customer = getCurrentCustomer(token);
        customer.setPassword(password);
        customerDao.save(customer);
    }

    public void updateCardDetails(String token, String cardDetails) {
        Customer customer = getCurrentCustomer(token);
        customer.setCreditCardDetails(cardDetails);
        customerDao.save(customer);
    }

    public void updateAddress(String token, String type, String newAddress) {
        Customer customer = getCurrentCustomer(token);
        if(Objects.equals(type, "home")){
            customer.setAddress(newAddress);
        }
        customerDao.save(customer);
    }

    public void deleteCustomer(String token) {
        Customer customer = getCurrentCustomer(token);
        customerDao.delete(customer);
    }

    public void deleteAddress(String token, String type) {
        Customer customer = getCurrentCustomer(token);
        if(Objects.equals(type, "home")){
            customer.setAddress(null);
        }
        customerDao.save(customer);
    }
}
