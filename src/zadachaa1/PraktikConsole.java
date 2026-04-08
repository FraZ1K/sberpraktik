package zadachaa1;

import java.util.ArrayList;
import java.util.Scanner;

public class PraktikConsole {
    private static ArrayList<zadanie3> currencies = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            printMenu();
            int choice = getIntInput("Выберите действие: ");

            switch (choice) {
                case 1:
                    addCurrency();
                    break;
                case 2:
                    showAllCurrencies();
                    break;
                case 3:
                    calculateTotalSum();
                    break;
                case 4:
                    System.out.println("До свидания!");
                    return;
                default:
                    System.out.println("Неверный выбор. Попробуйте снова.");
            }
        }
    }

    private static void printMenu() {
        System.out.println("\n========== УЧЁТ КУПЮР ==========");
        System.out.println("1. Добавить купюры");
        System.out.println("2. Показать все купюры");
        System.out.println("3. Рассчитать общую сумму");
        System.out.println("4. Выход");
        System.out.println("=================================");
    }

    private static void addCurrency() {
        System.out.println("\n--- Добавление купюр ---");
        int denomination = getIntInput("Введите номинал купюры (1, 2, 5, 10, 50, 100...): ");
        int quantity = getIntInput("Введите количество купюр: ");

        try {
            zadanie3 currency = new zadanie3(denomination, quantity);
            currencies.add(currency);
            System.out.println("✓ Добавлено: " + currency.getInfo());
        } catch (IllegalArgumentException e) {
            System.out.println("✗ Ошибка: " + e.getMessage());
        }
    }

    private static void showAllCurrencies() {
        if (currencies.isEmpty()) {
            System.out.println("\nСписок купюр пуст.");
            return;
        }

        System.out.println("\n--- СПИСОК КУПЮР ---");
        for (int i = 0; i < currencies.size(); i++) {
            System.out.println((i + 1) + ". " + currencies.get(i).getInfo());
        }
        System.out.println("Всего позиций: " + currencies.size());
    }

    private static void calculateTotalSum() {
        if (currencies.isEmpty()) {
            System.out.println("\nНет купюр для расчёта.");
            return;
        }

        int total = 0;
        System.out.println("\n--- РАСЧЁТ СУММЫ ---");
        for (zadanie3 c : currencies) {
            int sum = c.calculateTotal();
            total += sum;
            System.out.printf("%d руб × %d шт = %d руб\n",
                    c.getDenomination(), c.getQuantity(), sum);
        }
        System.out.println("-------------------");
        System.out.println("ОБЩАЯ СУММА: " + total + " руб");
        System.out.println("-------------------");
    }

    private static int getIntInput(String prompt) {
        System.out.print(prompt);
        while (!scanner.hasNextInt()) {
            System.out.print("Ошибка! Введите число: ");
            scanner.next();
        }
        return scanner.nextInt();
    }
}