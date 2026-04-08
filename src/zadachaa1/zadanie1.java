package zadachaa1;

import java.util.Scanner;

public class zadanie1 {
    public zadanie1() {
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        try {
            System.out.print("Введите размер размера N: ");
            int n = scanner.nextInt();
            if (n > 0) {
                int[] array = new int[n];
                System.out.println("Введите " + n + " целых чисел");

                int min;
                for(min = 0; min < n; ++min) {
                    System.out.println("" + min + " =");
                    array[min] = scanner.nextInt();
                }

                min = array[0];
                int minIndex = 0;

                int i;
                for(i = 1; i < n; ++i) {
                    if (array[i] < min) {
                        min = array[i];
                        minIndex = i;
                    }
                }

                for(i = 0; i < n; ++i) {
                    System.out.print(array[i] + " ");
                }

                System.out.println("Наименьший элемент: " + min);
                System.out.println("Индекс элемента: " + minIndex);
                return;
            }

            System.out.println("Error: N должно быть положительным числом!");
        } catch (Exception var10) {
            Exception e = var10;
            System.out.println("Error:" + e.getMessage());
            return;
        } finally {
            scanner.close();
        }

    }
}