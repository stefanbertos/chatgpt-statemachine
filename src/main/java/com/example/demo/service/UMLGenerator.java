package com.example.demo.service;

import com.example.demo.dto.OrderEvent;
import com.example.demo.dto.OrderState;
import net.sourceforge.plantuml.SourceStringReader;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;

public class UMLGenerator {

    public static void generateUMLImage(Map<OrderState, Map<OrderEvent, OrderState>> transitionMap, String filePath) throws IOException {
        StringBuilder uml = new StringBuilder();
        uml.append("@startuml\n");

        for (Map.Entry<OrderState, Map<OrderEvent, OrderState>> entry : transitionMap.entrySet()) {
            OrderState sourceState = entry.getKey();
            for (Map.Entry<OrderEvent, OrderState> transition : entry.getValue().entrySet()) {
                OrderEvent event = transition.getKey();
                OrderState targetState = transition.getValue();
                uml.append(sourceState).append(" --> ").append(targetState).append(" : ").append(event).append("\n");
            }
        }

        uml.append("@enduml");

        SourceStringReader reader = new SourceStringReader(uml.toString());
        try (FileOutputStream outputStream = new FileOutputStream(filePath)) {
            reader.generateImage(outputStream);
        }
    }
}
