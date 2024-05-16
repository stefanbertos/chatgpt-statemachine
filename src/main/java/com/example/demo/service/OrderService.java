package com.example.demo.service;

import com.example.demo.dto.*;
import com.example.demo.repository.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class OrderService {

    private final OrderDao orderDao;
    private Map<OrderState, Map<OrderEvent, OrderState>> transitionMap;

    public OrderService(OrderDao orderDao) {
        this.orderDao = orderDao;
    }

    @PostConstruct
    private void init() {
        TransitionBuilder builder = new TransitionBuilder();
        builder.addTransition(OrderState.NEW_ORDER, OrderEvent.ORDER_CREATED, OrderState.PENDING_PAYMENT)
                .addTransition(OrderState.PENDING_PAYMENT, OrderEvent.PAYMENT_CONFIRMED, OrderState.PAYMENT_RECEIVED)
                .addTransition(OrderState.PENDING_PAYMENT, OrderEvent.PAYMENT_FAILED, OrderState.CANCELLED)
                .addTransition(OrderState.PENDING_PAYMENT, OrderEvent.CUSTOMER_CANCELLATION, OrderState.CANCELLED)
                .addTransition(OrderState.PAYMENT_RECEIVED, OrderEvent.ORDER_PACKED, OrderState.PROCESSING)
                .addTransition(OrderState.PROCESSING, OrderEvent.ORDER_SHIPPED, OrderState.SHIPPED)
                .addTransition(OrderState.SHIPPED, OrderEvent.ORDER_DELIVERED, OrderState.DELIVERED)
                .addTransition(OrderState.DELIVERED, OrderEvent.RETURN_REQUESTED, OrderState.RETURNED)
                .addTransition(OrderState.RETURNED, OrderEvent.REFUND_PROCESSED, OrderState.REFUNDED);

        transitionMap = new HashMap<>();
        List<Transition> transitions = builder.build();
        for (Transition transition : transitions) {
            transitionMap
                    .computeIfAbsent(transition.sourceState(), k -> new HashMap<>())
                    .put(transition.event(), transition.targetState());
        }

        try {
            // Ensure the directory exists
            Files.createDirectories(Paths.get("src/main/resources/static"));

            // Generate the UML diagram image
            UMLGenerator.generateUMLImage(transitionMap, "src/main/resources/static/state_machine_diagram.png");
            log.info("State machine UML diagram generated at: src/main/resources/static/state_machine_diagram.png");
        } catch (IOException e) {
            log.error("Failed to generate UML diagram", e);
        }
    }

    public OrderState handleEvent(String orderId, OrderEvent event) {
        Order order = orderDao.findById(orderId);

        if (order == null) {
            if (event == OrderEvent.ORDER_CREATED) {
                // Initialize a new order
                order = new Order(orderId);
                log.info("New order created with ID: {}", orderId);
            } else {
                log.warn("Invalid order ID: {} for event: {}", orderId, event);
                return null;
            }
        }

        OrderState currentState = order.state();
        OrderState newState = transitionMap.getOrDefault(currentState, new HashMap<>()).get(event);

        if (newState != null) {
            order = order.addStateTransition(currentState, newState, event.toString());
            orderDao.save(order);
            log.info("Transitioned from {} to {} on event {}", currentState, newState, event);
            return newState;
        } else {
            log.warn("Invalid event: {} for current state: {}", event, currentState);
            return currentState;
        }
    }

    public OrderState getCurrentState(String orderId) {
        Order order = orderDao.findById(orderId);
        return order != null ? order.state() : null;
    }

    public List<StateTransition> getStateHistory(String orderId) {
        Order order = orderDao.findById(orderId);
        return order != null ? order.stateHistory() : null;
    }
}
