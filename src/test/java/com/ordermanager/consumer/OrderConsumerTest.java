package com.ordermanager.consumer;

import com.ordermanager.model.Order;
import com.ordermanager.annotations.OrderType;
import org.junit.jupiter.api.Test;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import static org.junit.jupiter.api.Assertions.*;

class OrderConsumerTest {

    @Test
    void testConsumerProcessesOrder() throws Exception {
        var queue = new LinkedBlockingQueue<Order>();
        var processed = new ConcurrentHashMap<String, Order>();
        var consumer = new OrderConsumer(queue, processed, "Test");

        Order order = new Order("Тест", 5, OrderType.Type.REGULAR);
        queue.put(order);

        Thread thread = new Thread(consumer);
        thread.start();
        Thread.sleep(1500);
        consumer.stop();
        thread.join();

        assertEquals(1, processed.size());
    }

    @Test
    void testConsumerProcessesUrgentOrderFaster() throws Exception {
        var queue = new LinkedBlockingQueue<Order>();
        var processed = new ConcurrentHashMap<String, Order>();
        var consumer = new OrderConsumer(queue, processed, "TestUrgent");

        Order urgentOrder = new Order("Срочный", 1, OrderType.Type.URGENT);

        long start = System.currentTimeMillis();
        queue.put(urgentOrder);

        Thread thread = new Thread(consumer);
        thread.start();
        Thread.sleep(600);
        consumer.stop();
        thread.join();

        long duration = System.currentTimeMillis() - start;
        assertTrue(duration < 1000);
        assertEquals(1, processed.size());
    }

    @Test
    void testConsumerHandlesMultipleOrders() throws Exception {
        var queue = new LinkedBlockingQueue<Order>();
        var processed = new ConcurrentHashMap<String, Order>();
        var consumer = new OrderConsumer(queue, processed, "TestMulti");

        for (int i = 0; i < 5; i++) {
            Order order = new Order("Заказ " + i, i + 1, OrderType.Type.REGULAR);
            queue.put(order);
        }

        Thread thread = new Thread(consumer);
        thread.start();
        Thread.sleep(3000);
        consumer.stop();
        thread.join();

        assertEquals(5, consumer.getOrdersProcessed());
    }

    @Test
    void testConsumerHandlesInvalidOrder() throws Exception {
        var queue = new LinkedBlockingQueue<Order>();
        var processed = new ConcurrentHashMap<String, Order>();
        var consumer = new OrderConsumer(queue, processed, "TestInvalid");

        Order invalidOrder = new Order();
        invalidOrder.setId(null);
        invalidOrder.setDescription("Тест");
        invalidOrder.setQuantity(5);
        invalidOrder.setOrderType(OrderType.Type.REGULAR);
        queue.put(invalidOrder);

        Thread thread = new Thread(consumer);
        thread.start();
        Thread.sleep(1500);
        consumer.stop();
        thread.join();

        assertEquals(0, processed.size());
        assertEquals(1, consumer.getFailedOrders());
    }
}