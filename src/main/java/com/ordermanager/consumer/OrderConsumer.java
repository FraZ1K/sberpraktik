package com.ordermanager.consumer;

import com.ordermanager.model.Order;
import com.ordermanager.validator.OrderValidator;
import java.time.LocalDateTime;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Logger;

public class OrderConsumer implements Runnable {
    private static final Logger logger = Logger.getLogger(OrderConsumer.class.getName());

    private final BlockingQueue<Order> queue;
    private final ConcurrentMap<String, Order> processedOrders;
    private final OrderValidator validator;
    private final AtomicBoolean running;
    private final String consumerName;
    private int ordersProcessed;
    private int failedOrders;

    public OrderConsumer(BlockingQueue<Order> queue,
                         ConcurrentMap<String, Order> processedOrders,
                         String consumerName) {
        this.queue = queue;
        this.processedOrders = processedOrders;
        this.validator = new OrderValidator();
        this.running = new AtomicBoolean(true);
        this.consumerName = consumerName;
        this.ordersProcessed = 0;
        this.failedOrders = 0;
    }

    @Override
    public void run() {
        logger.info(consumerName + " started...");

        try {
            while (running.get()) {
                Order order = queue.poll();
                if (order == null) {
                    Thread.sleep(100);
                    continue;
                }
                processOrder(order);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            logger.warning(consumerName + " was interrupted");
        }

        logger.info(String.format("%s finished. Processed: %d, Failed: %d",
                consumerName, ordersProcessed, failedOrders));
    }

    private void processOrder(Order order) {
        order.setStatus(Order.OrderStatus.IN_PROCESSING);

        try {
            validator.validateOrThrow(order);
            int processingTime = order.getOrderType() == com.ordermanager.annotations.OrderType.Type.URGENT ? 500 : 1000;
            Thread.sleep(processingTime);
            order.setProcessedAt(LocalDateTime.now());
            order.setStatus(Order.OrderStatus.COMPLETED);
            processedOrders.put(order.getId(), order);
            ordersProcessed++;
            logger.info(String.format("[%s] Обработан заказ: %s (тип: %s, время: %d мс)",
                    consumerName, order.getId(), order.getOrderType().getDescription(), processingTime));
        } catch (OrderValidator.ValidationException e) {
            order.setStatus(Order.OrderStatus.FAILED);
            failedOrders++;
            logger.warning(String.format("[%s] Ошибка валидации заказа %s: %s",
                    consumerName, order.getId(), e.getMessage()));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            logger.warning(consumerName + " was interrupted during order processing");
        }
    }

    public void stop() {
        running.set(false);
    }

    public int getOrdersProcessed() {
        return ordersProcessed;
    }

    public int getFailedOrders() {
        return failedOrders;
    }
}