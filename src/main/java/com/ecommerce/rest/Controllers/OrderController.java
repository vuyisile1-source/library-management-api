package com.ecommerce.rest.Controllers;

import com.ecommerce.rest.Models.Order;
import com.ecommerce.rest.Services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/order/place")
    public String placeOrder(@RequestHeader("Authorization") String token) {
        orderService.placeOrder(token);
        return "Order placed successfully!";
    }

    @GetMapping("/orders/{id}")
    public Order getOrderById(@PathVariable long id, @RequestHeader("Authorization") String token) {
        return orderService.getOrderById(token, id);
    }

    @GetMapping("/orders")
    public List<Order> getAllOrders(@RequestHeader("Authorization") String token) {
        return orderService.getAllOrders(token);
    }

    @GetMapping("/orders/by/date")
    public List<Order> getOrdersByDate(@RequestBody String date, @RequestHeader("Authorization") String token) {
        return orderService.getOrdersByDate(token, date);
    }

    @PutMapping("/orders/{id}")
    public String updateOrder(@PathVariable long id, @RequestBody Order order, @RequestHeader("Authorization") String token) {
        orderService.updateOrder(token, id, order);
        return "Order updated successfully!";
    }

    @DeleteMapping("/orders/{id}")
    public String cancelOrder(@PathVariable long id, @RequestHeader("Authorization") String token) {
        orderService.cancelOrder(token, id);
        return "Order canceled successfully!";
    }
}
