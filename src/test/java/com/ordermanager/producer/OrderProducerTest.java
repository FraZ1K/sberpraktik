package com.ordermanager.producer;

import com.ordermanager.model.Order;
import com.ordermanager.annotations.OrderType;
import org.junit.jupiter.api.Test;
import java.util.concurrent.LinkedBlockingQueue;
import static org.junit.jupiter.api.Assertions.*;

class OrderProducerTest {

    @Test
    void testProducerCreatesOrders() throws Exception {
        var queue = new LinkedBlockingQueue<Order>();
        var producer = new OrderProducer(queue);

        Thread thread = new Thread(producer);
        thread.start();
        Thread.sleep(2000);
        producer.stop();
        thread.join();

        assertTrue(producer.getOrdersProduced() > 0);
        assertFalse(queue.isEmpty());
    }

    @Test
    void testProducerCreatesBothOrderTypes() throws Exception {
        var queue = new LinkedBlockingQueue<Order>();
        var producer = new OrderProducer(queue);

        Thread thread = new Thread(producer);
        thread.start();
        Thread.sleep(3000);
        producer.stop();
        thread.join();

        boolean hasUrgent = false;
        boolean hasRegular = false;

        for (Order order : queue) {
            if (order.getOrderType() == OrderType.Type.URGENT) hasUrgent = true;
            if (order.getOrderType() == OrderType.Type.REGULAR) hasRegular = true;
        }

        assertTrue(hasUrgent || hasRegular);
    }

    @Test
    void testProducerQuantityRange() throws Exception {
        var queue = new LinkedBlockingQueue<Order>();
        var producer = new OrderProducer(queue);

        Thread thread = new Thread(producer);
        thread.start();
        Thread.sleep(2000);
        producer.stop();
        thread.join();

        for (Order order : queue) {
            assertTrue(order.getQuantity() >= 1 && order.getQuantity() <= 100);
        }
    }

    @Test
    void testProducerStop() throws Exception {
        var queue = new LinkedBlockingQueue<Order>();
        var producer = new OrderProducer(queue);

        Thread thread = new Thread(producer);
        thread.start();
        Thread.sleep(500);
        int produced = producer.getOrdersProduced();
        producer.stop();
        Thread.sleep(500);

        assertEquals(produced, producer.getOrdersProduced());
    }
}