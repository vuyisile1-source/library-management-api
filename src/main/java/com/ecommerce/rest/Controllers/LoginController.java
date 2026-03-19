package com.ecommerce.rest.Controllers;

import com.ecommerce.rest.Dto.LoginRequest;
import com.ecommerce.rest.Models.Customer;
import com.ecommerce.rest.Models.Seller;
import com.ecommerce.rest.Models.User;
import com.ecommerce.rest.Services.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class LoginController {

    @Autowired
    private LoginService loginService;

    @PostMapping("/register/customer")
    public String registerCustomer(@RequestBody Customer user) {
        return loginService.registerCustomer(user);
    }

    @PostMapping("/login/customer")
    public String loginCustomer(@RequestBody LoginRequest login) {
        return loginService.loginCustomer(login);
    }

    @PostMapping("/logout/customer")
    public String logoutCustomer(@RequestHeader("Authorization") String sessionToken) {
        System.out.println("customer.getSessionToken()");
        loginService.logout(sessionToken);
        return "Customer logged out successfully!";
    }

    @PostMapping("/register/seller")
    public String registerSeller(@RequestBody Seller user) {
        return loginService.registerSeller(user);
    }

    @PostMapping("/login/seller")
    public String loginSeller(@RequestBody LoginRequest login) {
        return loginService.loginSeller(login);
    }

    @PostMapping("/logout/seller")
    public String logoutSeller(@RequestHeader("Authorization") String sessionToken) {
        loginService.logout(sessionToken);
        return "Seller logged out successfully!";
    }
}
