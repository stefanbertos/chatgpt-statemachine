package com.example.demo.dto;

public record Transition(OrderState sourceState, OrderEvent event, OrderState targetState) {}
