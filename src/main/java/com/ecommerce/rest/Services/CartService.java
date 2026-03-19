package com.ecommerce.rest.Services;

import com.ecommerce.rest.Dao.CartDao;
import com.ecommerce.rest.Dao.CartItemDao;
import com.ecommerce.rest.Dao.CustomerDao;
import com.ecommerce.rest.Dto.CartDeleteRequest;
import com.ecommerce.rest.Models.Cart;
import com.ecommerce.rest.Models.CartItem;
import com.ecommerce.rest.Models.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CartService {

    @Autowired
    private CartDao cartDao;

    @Autowired
    private CartItemDao cartItemDao;

    @Autowired
    private CustomerDao customerDao;

    public List<CartItem> getCartItems(String token) {
        Cart cart = validateCustomerAndGetCart(token);
        return cart.getCartItems();
    }

    public void addItemToCart(String token, CartItem cartItem) {
        Cart cart = validateCustomerAndGetCart(token);
        cartItem.setCart(cart);
        cart.getCartItems().add(cartItem);
        cartDao.save(cart);
    }

    public void removeItemFromCart(String token, CartDeleteRequest itemId) {
        Cart cart = validateCustomerAndGetCart(token);
        CartItem item = cartItemDao.findById(itemId.getItemId())
                .orElseThrow(() -> new RuntimeException("Cart item not found"));

        if (!cart.getCartItems().contains(item)) {
            throw new RuntimeException("Item does not belong to this cart");
        }

        cart.getCartItems().remove(item);
        cartItemDao.delete(item);
        cartDao.save(cart);
    }

    public void clearCart(String token) {
        Cart cart = validateCustomerAndGetCart(token);
        cart.getCartItems().clear();
        cartDao.save(cart);
    }

    private Cart validateCustomerAndGetCart(String token) {
        Customer customer = customerDao.findBySessionToken(token)
                .orElseThrow(() -> new RuntimeException("Invalid or expired session token"));
        return cartDao.findByCustomerId(customer.getId())
                .orElseThrow(() -> new RuntimeException("Cart not found for customer"));
    }
}
