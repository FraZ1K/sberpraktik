package com.ordermanager.producer;

import com.ordermanager.model.Order;
import com.ordermanager.annotations.OrderType;
import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Logger;

public class OrderProducer implements Runnable {
    private static final Logger logger = Logger.getLogger(OrderProducer.class.getName());

    private final BlockingQueue<Order> queue;
    private final AtomicBoolean running;
    private final Random random;
    private int ordersProduced;

    public OrderProducer(BlockingQueue<Order> queue) {
        this.queue = queue;
        this.running = new AtomicBoolean(true);
        this.random = new Random();
        this.ordersProduced = 0;
    }

    @Override
    public void run() {
        logger.info("Producer started...");

        try {
            while (running.get() && ordersProduced < 20) {
                Order order = createRandomOrder();
                queue.put(order);
                ordersProduced++;
                logger.info(String.format("[PRODUCER] Создан заказ #%d: %s", ordersProduced, order));
                Thread.sleep(500 + random.nextInt(1000));
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            logger.warning("Producer was interrupted");
        }

        logger.info("Producer finished. Total orders produced: " + ordersProduced);
    }

    private Order createRandomOrder() {
        String[] descriptions = {"Ноутбук", "Смартфон", "Книга", "Клавиатура", "Мышь", "Монитор", "Наушники"};
        String description = descriptions[random.nextInt(descriptions.length)] + " #" + System.currentTimeMillis();
        int quantity = 1 + random.nextInt(100);
        OrderType.Type orderType = random.nextBoolean() ? OrderType.Type.URGENT : OrderType.Type.REGULAR;
        return new Order(description, quantity, orderType);
    }

    public void stop() {
        running.set(false);
    }

    public int getOrdersProduced() {
        return ordersProduced;
    }
}