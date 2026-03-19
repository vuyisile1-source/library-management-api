package com.ecommerce.rest.Controllers;

import com.ecommerce.rest.Models.Customer;
import com.ecommerce.rest.Models.Order;
import com.ecommerce.rest.Services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @GetMapping("/customer/current")
    public Customer getCurrentCustomer(@RequestHeader("Authorization") String sessionToken) {
        return customerService.getCurrentCustomer(sessionToken);
    }

    @GetMapping("/customer/orders")
    public List<Order> getCustomerOrders(@RequestHeader("Authorization") String sessionToken) {
        return customerService.getCustomerOrders(sessionToken);
    }

    @GetMapping("/customers")
    public List<Customer> getAllCustomers() {
        return customerService.getAllCustomers();
    }

    @PutMapping("/customer")
    public String updateCustomer(@RequestHeader("Authorization") String sessionToken, @RequestBody Customer customer) {
        customerService.updateCustomer(sessionToken, customer);
        return "Customer updated successfully!";
    }

    @PutMapping("/customer/update/credentials")
    public String updateCredentials(@RequestHeader("Authorization") String sessionToken, @RequestBody Customer customer) {
        customerService.updateCustomer(sessionToken, customer);
        return "Customer credentials updated successfully!";
    }

    @PutMapping("/customer/update/password")
    public String updatePassword(@RequestHeader("Authorization") String sessionToken, @RequestBody String password) {
        customerService.updatePassword(sessionToken, password);
        return "Password updated successfully!";
    }

    @PutMapping("/customer/update/card")
    public String updateCardDetails(@RequestHeader("Authorization") String sessionToken, @RequestBody String cardDetails) {
        customerService.updateCardDetails(sessionToken, cardDetails);
        return "Credit card details updated!";
    }

    @PutMapping("/customer/update/address")
    public String updateAddress(@RequestHeader("Authorization") String sessionToken, @RequestParam String type, @RequestBody String newAddress) {
        customerService.updateAddress(sessionToken, type, newAddress);
        return "Address updated successfully!";
    }

    @DeleteMapping("/customer")
    public String deleteCustomer(@RequestHeader("Authorization") String sessionToken) {
        customerService.deleteCustomer(sessionToken);
        return "Customer deleted successfully!";
    }

    @DeleteMapping("/customer/delete/address")
    public String deleteAddress(@RequestHeader("Authorization") String sessionToken, @RequestParam String type) {
        customerService.deleteAddress(sessionToken, type);
        return "Address deleted successfully!";
    }
}
