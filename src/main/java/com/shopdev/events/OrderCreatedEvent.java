package com.shopdev.events;

import java.math.BigDecimal;

public record OrderCreatedEvent(String orderId, String customerName, BigDecimal amount) {}


