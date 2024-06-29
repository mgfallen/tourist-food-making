package org.example.controllers;

import org.example.entities.Order;
import org.example.services.OrderDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/orders")
public class OrderController {
    @Autowired
    private final OrderDataService orderDataService;

    public OrderController(OrderDataService orderDataService) {
        this.orderDataService = orderDataService;
    }



    @GetMapping
    public ResponseEntity<List<Order>> getAllOrders() {
        List<Order> orders = orderDataService.getAllOrders();
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/{order_id}")
    public ResponseEntity<Order> getOrderById(@PathVariable("order_id") Long orderId) {
        Optional<Order> order = orderDataService.getOrderById(orderId);
        return order.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
