package com.ordermanager.model;

import com.ordermanager.annotations.OrderType;
import com.ordermanager.annotations.Validate;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

public class Order {

    @Validate(message = "ID заказа не может быть null", required = true)
    private String id;

    @Validate(message = "Описание заказа не может быть пустым",
            required = true, minLength = 1, maxLength = 200)
    private String description;

    @OrderType(OrderType.Type.REGULAR)
    private OrderType.Type orderType;

    @Validate(message = "Количество товара должно быть от 1 до 1000",
            required = true, min = 1, max = 1000)
    private int quantity;

    private LocalDateTime createdAt;
    private LocalDateTime processedAt;
    private OrderStatus status;

    public enum OrderStatus {
        CREATED("Создан"),
        IN_PROCESSING("В обработке"),
        COMPLETED("Завершен"),
        FAILED("Ошибка");

        private final String description;

        OrderStatus(String description) {
            this.description = description;
        }

        public String getDescription() {
            return description;
        }
    }

    public Order() {
        this.id = UUID.randomUUID().toString();
        this.createdAt = LocalDateTime.now();
        this.status = OrderStatus.CREATED;
        this.orderType = OrderType.Type.REGULAR;
    }

    public Order(String description, int quantity, OrderType.Type orderType) {
        this();
        this.description = description;
        this.quantity = quantity;
        this.orderType = orderType;
    }

    // Геттеры
    public String getId() { return id; }
    public String getDescription() { return description; }
    public OrderType.Type getOrderType() { return orderType; }
    public int getQuantity() { return quantity; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getProcessedAt() { return processedAt; }
    public OrderStatus getStatus() { return status; }

    // Сеттеры
    public void setId(String id) { this.id = id; }
    public void setDescription(String description) { this.description = description; }
    public void setOrderType(OrderType.Type orderType) { this.orderType = orderType; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public void setProcessedAt(LocalDateTime processedAt) { this.processedAt = processedAt; }
    public void setStatus(OrderStatus status) { this.status = status; }

    @Override
    public String toString() {
        return String.format("Order{id='%s', description='%s', type=%s, quantity=%d, status=%s}",
                id, description, orderType.getDescription(), quantity, status.getDescription());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return Objects.equals(id, order.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}