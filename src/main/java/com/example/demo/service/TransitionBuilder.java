package com.example.demo.service;

import com.example.demo.dto.OrderEvent;
import com.example.demo.dto.OrderState;
import com.example.demo.dto.Transition;

import java.util.ArrayList;
import java.util.List;

public class TransitionBuilder {
    private final List<Transition> transitions = new ArrayList<>();

    public TransitionBuilder addTransition(OrderState sourceState, OrderEvent event, OrderState targetState) {
        transitions.add(new Transition(sourceState, event, targetState));
        return this;
    }

    public List<Transition> build() {
        return transitions;
    }
}
