package com.example.demo.dto;

public enum OrderState {
    NEW_ORDER,
    PENDING_PAYMENT,
    PAYMENT_RECEIVED,
    PROCESSING,
    SHIPPED,
    DELIVERED,
    CANCELLED,
    RETURNED,
    REFUNDED
}
