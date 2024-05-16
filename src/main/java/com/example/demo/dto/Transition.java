package com.example.demo.dto;

import com.example.demo.service.Action;

public record Transition(OrderState sourceState, OrderEvent event, OrderState targetState, long timeoutSeconds, OrderState timeoutFallbackState, Action action) {

    public Transition(OrderState sourceState, OrderEvent event, OrderState targetState) {
        this(sourceState, event, targetState, 0, null, null);
    }

    public Transition(OrderState sourceState, OrderEvent event, OrderState targetState, long timeoutSeconds, OrderState timeoutFallbackState) {
        this(sourceState, event, targetState, timeoutSeconds, timeoutFallbackState, null);
    }

    public Transition(OrderState sourceState, OrderEvent event, OrderState targetState, long timeoutSeconds, OrderState timeoutFallbackState, Action action) {
        this.sourceState = sourceState;
        this.event = event;
        this.targetState = targetState;
        this.timeoutSeconds = timeoutSeconds;
        this.timeoutFallbackState = timeoutFallbackState;
        this.action = action;
    }
}
