package com.example.demo.service;



import com.example.demo.dto.Order;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class PaymentAction implements Action {

    @Override
    public boolean execute(Order order) {
        // Implement payment processing logic here
        // For example, call an external payment provider API and handle the response
        log.info("Processing payment for order ID: {}", order.id());
        // Simulate payment processing
        try {
            Thread.sleep(2000); // Simulate delay
        } catch (InterruptedException e) {
            log.error("Payment processing interrupted for order ID: {}", order.id(), e);
            return false;
        }
        // Simulate successful payment
        return true;
    }
}
