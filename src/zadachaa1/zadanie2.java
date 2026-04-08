package zadachaa1;

import java.util.Scanner;

public class zadanie2 {
    private static final int ARRAY_SIZE = 20;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int[] array = new int[20];
        System.out.println("Введите 20 целых чисел: ");

        for(int i = 0; i < 20; ++i) {
            System.out.print("Элемент " + (i + 1) + " :");

            while(!scanner.hasNextInt()) {
                System.out.println("Error: Введите целое число.");
                scanner.next();
                System.out.print("Элемент " + (i + 1) + ": ");
            }

            array[i] = scanner.nextInt();
        }

        int evenCount = 0;
        int oddCount = 0;

        for(int i = 0; i < 20; ++i) {
            System.out.print(array[i]);
            if ((i + 1) % 10 == 0) {
                System.out.println();
            }

            if (array[i] % 2 == 0) {
                ++evenCount;
            } else {
                ++oddCount;
            }
        }

        System.out.println("Чётных элементов: " + evenCount);
        System.out.println("Нечётных элементов: " + oddCount);
        System.out.println();
        if (evenCount > oddCount) {
            System.out.println("Чётных элементов БОЛЬШЕ");
            System.out.println("Разница: " + (evenCount - oddCount));
        } else if (oddCount > evenCount) {
            System.out.println("Нечётных элементов БОЛЬШЕ");
            System.out.println("Разница: " + (oddCount - evenCount));
        } else {
            System.out.println("Чётных и нечётных ПОРОВНУ");
        }

    }
}