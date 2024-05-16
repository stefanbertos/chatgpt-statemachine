package com.example.demo.service;

import com.example.demo.dto.Order;

public interface Action {
    boolean execute(Order order);
}