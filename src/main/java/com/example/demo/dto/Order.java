package com.example.demo.dto;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "orders")
public record Order(
        @Id String id,
        OrderState state,
        List<StateTransition> stateHistory
) {
    public Order {
        if (stateHistory == null) {
            stateHistory = new ArrayList<>();
        }
    }

    public Order(String id) {
        this(id, OrderState.NEW_ORDER, new ArrayList<>(List.of(new StateTransition(null, OrderState.NEW_ORDER, "Order created"))));
    }

    public Order addStateTransition(OrderState fromState, OrderState toState, String reason) {
        List<StateTransition> newHistory = new ArrayList<>(this.stateHistory);
        newHistory.add(new StateTransition(fromState, toState, reason));
        return new Order(this.id, toState, newHistory);
    }
}
