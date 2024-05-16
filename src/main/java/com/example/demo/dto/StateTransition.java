package com.example.demo.dto;

import java.time.LocalDateTime;

public record StateTransition(
        OrderState fromState,
        OrderState toState,
        LocalDateTime timestamp,
        String reason
) {
    public StateTransition(OrderState fromState, OrderState toState, String reason) {
        this(fromState, toState, LocalDateTime.now(), reason);
    }
}
