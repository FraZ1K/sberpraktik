package com.ordermanager.model;

import com.ordermanager.annotations.OrderType;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;

class OrderTest {

    @Test
    void testOrderCreation() {
        Order order = new Order("Тестовый заказ", 10, OrderType.Type.URGENT);
        assertNotNull(order.getId());
        assertEquals("Тестовый заказ", order.getDescription());
        assertEquals(10, order.getQuantity());
        assertEquals(OrderType.Type.URGENT, order.getOrderType());
    }

    @Test
    void testOrderSetters() {
        Order order = new Order();
        order.setId("TEST-001");
        order.setDescription("Измененный заказ");
        order.setQuantity(25);
        order.setOrderType(OrderType.Type.REGULAR);

        assertEquals("TEST-001", order.getId());
        assertEquals("Измененный заказ", order.getDescription());
        assertEquals(25, order.getQuantity());
        assertEquals(OrderType.Type.REGULAR, order.getOrderType());
    }

    @Test
    void testOrderStatus() {
        Order order = new Order();
        order.setStatus(Order.OrderStatus.COMPLETED);
        assertEquals(Order.OrderStatus.COMPLETED, order.getStatus());
    }

    @Test
    void testOrderGettersAndSetters() {
        Order order = new Order();
        LocalDateTime now = LocalDateTime.now();

        order.setCreatedAt(now);
        order.setProcessedAt(now);
        order.setStatus(Order.OrderStatus.IN_PROCESSING);

        assertEquals(now, order.getCreatedAt());
        assertEquals(now, order.getProcessedAt());
        assertEquals(Order.OrderStatus.IN_PROCESSING, order.getStatus());
    }

    @Test
    void testOrderHashCodeAndEquals() {
        Order order1 = new Order();
        Order order2 = new Order();
        order2.setId(order1.getId());

        assertEquals(order1, order2);
        assertEquals(order1.hashCode(), order2.hashCode());

        Order order3 = new Order();
        assertNotEquals(order1, order3);
    }

    @Test
    void testOrderConstructor() {
        Order order = new Order();
        assertNotNull(order.getId());
        assertNotNull(order.getCreatedAt());
        assertEquals(Order.OrderStatus.CREATED, order.getStatus());
        assertEquals(OrderType.Type.REGULAR, order.getOrderType());
    }
}