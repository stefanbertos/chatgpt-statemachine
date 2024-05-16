package com.example.demo.service;

import com.example.demo.dto.OrderEvent;
import com.example.demo.dto.OrderState;
import com.example.demo.dto.Transition;
import net.sourceforge.plantuml.SourceStringReader;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;

public class UMLGenerator {

    public static void generateUMLImage(Map<OrderState, Map<OrderEvent, Transition>> transitionMap, String filePath) throws IOException {
        StringBuilder uml = new StringBuilder();
        uml.append("@startuml\n");

        for (Map.Entry<OrderState, Map<OrderEvent, Transition>> entry : transitionMap.entrySet()) {
            OrderState sourceState = entry.getKey();
            for (Map.Entry<OrderEvent, Transition> transitionEntry : entry.getValue().entrySet()) {
                Transition transition = transitionEntry.getValue();
                uml.append(sourceState).append(" --> ").append(transition.targetState()).append(" : ").append(transition.event()).append("\n");
                if (transition.timeoutSeconds() > 0 && transition.timeoutFallbackState() != null) {
                    uml.append(sourceState).append(" --> ").append(transition.timeoutFallbackState()).append(" : Timeout(").append(transition.timeoutSeconds()).append("s)").append("\n");
                }
            }
        }

        uml.append("@enduml");

        SourceStringReader reader = new SourceStringReader(uml.toString());
        try (FileOutputStream outputStream = new FileOutputStream(filePath)) {
            reader.generateImage(outputStream);
        }
    }
}