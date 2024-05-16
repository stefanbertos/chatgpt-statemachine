package com.example.demo.dto;

public record Transition(OrderState sourceState, OrderEvent event, OrderState targetState, long timeoutSeconds, OrderState timeoutFallbackState) {

    public Transition(OrderState sourceState, OrderEvent event, OrderState targetState) {
        this(sourceState, event, targetState, 0, null);
    }

    public Transition(OrderState sourceState, OrderEvent event, OrderState targetState, long timeoutSeconds) {
        this(sourceState, event, targetState, timeoutSeconds, null);
    }
}