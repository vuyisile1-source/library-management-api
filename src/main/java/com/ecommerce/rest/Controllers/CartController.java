package com.ecommerce.rest.Controllers;

import com.ecommerce.rest.Dao.CustomerDao;
import com.ecommerce.rest.Dto.CartDeleteRequest;
import com.ecommerce.rest.Models.CartItem;
import com.ecommerce.rest.Models.Customer;
import com.ecommerce.rest.Services.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private CustomerDao customerDao;

    @GetMapping("/cart")
    public List<CartItem> getCartItems(@RequestHeader("Authorization") String token) {
        return cartService.getCartItems(token);
    }

    @PostMapping("/cart/add")
    public String addItemToCart(@RequestHeader("Authorization") String token, @RequestBody CartItem cartItem) {
        cartService.addItemToCart(token, cartItem);
        return "Item added to cart successfully!";
    }

    @DeleteMapping("/cart")
    public String removeItemFromCart(@RequestHeader("Authorization") String token, @RequestBody CartDeleteRequest itemId) {
        cartService.removeItemFromCart(token, itemId);
        return "Item removed from cart successfully!";
    }

    @DeleteMapping("/cart/clear")
    public String clearCart(@RequestHeader("Authorization") String token) {
        cartService.clearCart(token);
        return "Cart cleared successfully!";
    }
}
