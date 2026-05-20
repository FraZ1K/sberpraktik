package com.ordermanager.manager;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class OrderManagerTest {

    @Test
    void testStartAndStop() {
        OrderManager manager = new OrderManager(2);
        assertDoesNotThrow(() -> {
            manager.start();
            Thread.sleep(1000);
            manager.stop();
        });
    }

    @Test
    void testStatistics() throws Exception {
        OrderManager manager = new OrderManager(2);
        manager.start();
        Thread.sleep(3000);
        String stats = manager.getStatistics();
        assertNotNull(stats);
        assertTrue(stats.contains("СТАТИСТИКА"));
        manager.stop();
    }

    @Test
    void testManagerWithUrgentOrders() throws Exception {
        OrderManager manager = new OrderManager(2);
        manager.start();
        Thread.sleep(5000);

        String stats = manager.getStatistics();
        assertTrue(stats.contains("Срочный") || stats.contains("Обычный"));
        manager.stop();
    }

    @Test
    void testGetProcessedOrder() throws Exception {
        OrderManager manager = new OrderManager(2);
        manager.start();
        Thread.sleep(3000);

        assertNull(manager.getProcessedOrder("non-existent-id"));
        manager.stop();
    }

    @Test
    void testIsAllOrdersProcessed() throws Exception {
        OrderManager manager = new OrderManager(2);
        manager.start();

        assertFalse(manager.isAllOrdersProcessed());

        Thread.sleep(15000);
        manager.stop();

        assertDoesNotThrow(() -> manager.isAllOrdersProcessed());
    }
}