package com.ecommerce.rest.Services;

import com.ecommerce.rest.Dao.CartDao;
import com.ecommerce.rest.Dao.CustomerDao;
import com.ecommerce.rest.Dao.SellerDao;
import com.ecommerce.rest.Dto.LoginRequest;
import com.ecommerce.rest.Models.Cart;
import com.ecommerce.rest.Models.Customer;
import com.ecommerce.rest.Models.Seller;
import com.ecommerce.rest.Models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static com.ecommerce.rest.Models.User.Role.CUSTOMER;
import static com.ecommerce.rest.Models.User.Role.SELLER;

@Service
public class LoginService {

    @Autowired
    private CustomerDao customerDao;

    @Autowired
    private SellerDao sellerDao;

    @Autowired
    private CartDao cartDao;

    public String registerCustomer(User user) {
        Customer customer = new Customer();
        customer.setName(user.getName());
        customer.setEmail(user.getEmail());
        customer.setMobileNumber(user.getMobileNumber());
        customer.setPassword(user.getPassword());
        customer.setRole(CUSTOMER);
        customerDao.save(customer);
        return "Customer registered successfully!";
    }

    public String registerSeller(User user) {
        Seller seller = new Seller();
        seller.setName(user.getName());
        seller.setEmail(user.getEmail());
        seller.setMobileNumber(user.getMobileNumber());
        seller.setPassword(user.getPassword());
        seller.setRole(SELLER);
        sellerDao.save(seller);
        return "Seller registered successfully!";
    }

    public String loginCustomer(LoginRequest loginRequest) {
        Optional<Customer> customerOptional = customerDao.findByMobileNumber(loginRequest.getMobileNumber());

        if (customerOptional.isEmpty()) {
            return "Customer not found";
        }

        Customer customer = customerOptional.get();
        if (!customer.getPassword().equals(loginRequest.getPassword())) {
            return "Error: Invalid password";
        }

        cartDao.findByCustomerId(customer.getId())
                .orElseGet(() -> {
                    Cart cart = new Cart();
                    cart.setCustomer(customer);
                    return cartDao.save(cart);
                });
        String sessionToken = UUID.randomUUID().toString();
        customer.setSessionToken(sessionToken);
        customer.setLastLoginTime(LocalDateTime.now());
        customerDao.save(customer);
        return "Successfully logged into a customer account as: " + customer.getName() + "("+ sessionToken +")";
    }

    public String loginSeller(LoginRequest loginRequest) {
        Optional<Seller> sellerOptional = sellerDao.findByMobileNumber(loginRequest.getMobileNumber());

        if (sellerOptional.isEmpty()) {
            return "Seller not found";
        }

        Seller seller = sellerOptional.get();
        if (!seller.getPassword().equals(loginRequest.getPassword())) {
            return "Error: Invalid password";
        }

        String sessionToken = UUID.randomUUID().toString();
        seller.setSessionToken(sessionToken);
        seller.setLastLoginTime(LocalDateTime.now());
        sellerDao.save(seller);
        return "Successfully logged into a seller account as: " + seller.getName() + "("+ sessionToken +")";
    }

    public String logout(String sessionToken) {
        Optional<Customer> customerOptional = customerDao.findBySessionToken(sessionToken);

        if (customerOptional.isPresent()) {
            Customer customer = customerOptional.get();
            customer.setSessionToken(null);
            customer.setLastLoginTime(null);
            customerDao.save(customer);
            return "Successfully logged out customer";
        }

        Optional<Seller> sellerOptional = sellerDao.findBySessionToken(sessionToken);
        if (sellerOptional.isPresent()) {
            Seller seller = sellerOptional.get();
            seller.setSessionToken(null);
            seller.setLastLoginTime(null);
            sellerDao.save(seller);
            return "Successfully logged out seller";
        }

        return "Error: Invalid token";
    }
}
