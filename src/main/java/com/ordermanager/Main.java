package com.ordermanager;

import com.ordermanager.manager.OrderManager;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== Система обработки заказов ===");
        System.out.println("Создание заказов: Producer (один поток)");
        System.out.println("Обработка заказов: Consumers (2 потока)");
        System.out.println("====================================\n");

        OrderManager orderManager = new OrderManager(2);

        // Запускаем систему
        orderManager.start();
        System.out.println("Система запущена. Ожидание обработки всех заказов...\n");

        // Ждем 20 секунд для обработки всех заказов
        try {
            Thread.sleep(20000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Останавливаем систему и выводим статистику
        System.out.println("\nОстановка системы...");
        orderManager.stop();
        System.out.println(orderManager.getStatistics());
    }
}