package com.ordermanager.manager;

import com.ordermanager.consumer.OrderConsumer;
import com.ordermanager.model.Order;
import com.ordermanager.producer.OrderProducer;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.logging.Logger;

public class OrderManager {
    private static final Logger logger = Logger.getLogger(OrderManager.class.getName());

    private final BlockingQueue<Order> orderQueue;
    private final ConcurrentMap<String, Order> processedOrders;
    private final ExecutorService executorService;
    private final List<OrderConsumer> consumers;
    private OrderProducer producer;

    public OrderManager(int consumerCount) {
        this.orderQueue = new LinkedBlockingQueue<>(50);
        this.processedOrders = new ConcurrentHashMap<>();
        this.executorService = Executors.newFixedThreadPool(consumerCount + 1);
        this.consumers = new ArrayList<>();

        for (int i = 1; i <= consumerCount; i++) {
            consumers.add(new OrderConsumer(orderQueue, processedOrders, "Consumer-" + i));
        }
    }

    public void start() {
        logger.info("Starting Order Manager...");
        producer = new OrderProducer(orderQueue);
        executorService.submit(producer);
        for (OrderConsumer consumer : consumers) {
            executorService.submit(consumer);
        }
    }

    public void stop() {
        logger.info("Stopping Order Manager...");
        if (producer != null) {
            producer.stop();
        }
        for (OrderConsumer consumer : consumers) {
            consumer.stop();
        }
        executorService.shutdown();
        try {
            if (!executorService.awaitTermination(30, TimeUnit.SECONDS)) {
                executorService.shutdownNow();
            }
        } catch (InterruptedException e) {
            executorService.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }

    public String getStatistics() {
        StringBuilder stats = new StringBuilder();
        stats.append("\n========== СТАТИСТИКА ==========\n");
        stats.append(String.format("Всего создано заказов: %d\n", producer != null ? producer.getOrdersProduced() : 0));
        stats.append(String.format("Всего обработано заказов: %d\n", processedOrders.size()));
        int totalFailed = consumers.stream().mapToInt(OrderConsumer::getFailedOrders).sum();
        stats.append(String.format("Неудачных заказов: %d\n", totalFailed));
        stats.append("\nДетали по потребителям:\n");
        for (int i = 0; i < consumers.size(); i++) {
            OrderConsumer consumer = consumers.get(i);
            stats.append(String.format("  Consumer-%d: обработано=%d, ошибок=%d\n",
                    i + 1, consumer.getOrdersProcessed(), consumer.getFailedOrders()));
        }
        stats.append("================================\n");
        return stats.toString();
    }

    public int getProcessedOrdersCount() {
        return processedOrders.size();
    }

    public boolean isAllOrdersProcessed() {
        if (producer == null) return false;
        return producer.getOrdersProduced() == processedOrders.size();
    }
}