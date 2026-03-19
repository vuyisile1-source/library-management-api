package com.ecommerce.rest.Services;

import com.ecommerce.rest.Dao.CartDao;
import com.ecommerce.rest.Dao.OrderDao;
import com.ecommerce.rest.Models.Cart;
import com.ecommerce.rest.Models.OrderItem;
import com.ecommerce.rest.Models.Customer;
import com.ecommerce.rest.Models.Order;
import com.ecommerce.rest.Dao.CustomerDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private CartDao cartDao;

    @Autowired
    private CustomerDao customerDao;

    public void placeOrder(String token) {
        Customer customer = validateCustomer(token);
        Cart cart = cartDao.findByCustomerId(customer.getId())
                .orElseThrow(() -> new RuntimeException("Cart not found for customer"));

        if (cart.getCartItems().isEmpty()) {
            throw new RuntimeException("Cart is empty");
        }

        Order order = new Order();
        order.setCustomer(customer);
        order.setOrderDate(LocalDateTime.now());
        order.setStatus("PENDING");
        order.setTotalPrice(cart.getCartItems().stream()
                .mapToDouble(item -> item.getQuantity() * item.getProduct().getPrice()).sum());

        List<OrderItem> orderItems = cart.getCartItems().stream()
                .map(cartItem -> {
                    OrderItem orderItem = new OrderItem();
                    orderItem.setProduct(cartItem.getProduct());
                    orderItem.setQuantity(cartItem.getQuantity());
                    orderItem.setPrice(cartItem.getProduct().getPrice());
                    orderItem.setOrder(order);
                    return orderItem;
                })
                .collect(Collectors.toList());

        order.setOrderItems(orderItems);
        orderDao.save(order);
        cart.getCartItems().clear();
        cartDao.save(cart);
    }

    public Order getOrderById(String token, long id) {
        validateCustomer(token);
        return orderDao.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));
    }

    public List<Order> getAllOrders(String token) {
        validateCustomer(token);
        return orderDao.findAll();
    }

    public List<Order> getOrdersByDate(String token, String date) {
        validateCustomer(token);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");

        LocalDate parsedDate = LocalDate.parse(date, formatter);
        return orderDao.findByOrderDate(parsedDate);
    }

    public void updateOrder(String token, long id, Order updatedOrder) {
        validateCustomer(token);
        Order order = orderDao.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        order.setStatus(updatedOrder.getStatus());
        orderDao.save(order);
    }

    public void cancelOrder(String token, long id) {
        validateCustomer(token);
        Order order = orderDao.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        orderDao.delete(order);
    }

    private Customer validateCustomer(String token) {
        return customerDao.findBySessionToken(token)
                .orElseThrow(() -> new RuntimeException("Invalid or expired session token"));
    }
}
