package com.example.demo.controller;

import com.example.demo.dto.OrderEvent;
import com.example.demo.service.OrderService;
import com.example.demo.dto.OrderState;
import com.example.demo.dto.StateTransition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/{orderId}/event")
    public String handleEvent(@PathVariable String orderId, @RequestBody OrderEvent event) {
        OrderState newState = orderService.handleEvent(orderId, event);
        return "Event handled: " + event + ". Current state: " + newState;
    }

    @GetMapping("/{orderId}/state")
    public String getCurrentState(@PathVariable String orderId) {
        return "Current state: " + orderService.getCurrentState(orderId);
    }

    @GetMapping("/{orderId}/history")
    public List<StateTransition> getStateHistory(@PathVariable String orderId) {
        return orderService.getStateHistory(orderId);
    }
}