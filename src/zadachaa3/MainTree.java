package zadachaa3;

import java.util.Arrays;

public class MainTree {
    public static void main(String[] args) {
        BinarySearchTree<Integer> tree = new BinarySearchTree<>();


        tree.add(50);
        tree.add(30);
        tree.add(70);
        tree.add(20);
        tree.add(40);
        tree.add(60);
        tree.add(80);

        System.out.println("Размер: " + tree.size());


        System.out.println("Есть 40? " + tree.contains(40));
        System.out.println("Есть 100? " + tree.contains(100));


        System.out.print("Элементы: ");
        for (Integer val : tree) {
            System.out.print(val + " ");
        }

        tree.remove(50);
        System.out.println("\nПосле удаления 50:");
        for (Integer val : tree) {
            System.out.print(val + " ");
        }

        Object[] arr = tree.toArray();
        System.out.println("\nМассив: " + Arrays.toString(arr));
    }
}