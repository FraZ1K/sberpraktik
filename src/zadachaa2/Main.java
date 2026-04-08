package zadachaa2;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        Car bmw = new Car("BMW", "X5", 2022, 5500000, 5, "Automatic");
        Truck volvo = new Truck("Volvo", "FH16", 2021, 8500000, 20.5, 3);
        ElectickCar tesla = new ElectickCar("Tesla", "Model 3", 2023, 4500000, 4, "Automatic", 600);

        ArrayList<Vehicle> vehicles = new ArrayList<>();
        vehicles.add(bmw);
        vehicles.add(volvo);
        vehicles.add(tesla);

        System.out.println("=== Все автомобили ===\n");

        for (Vehicle v : vehicles) {
            v.displayInfo();
            v.startEngine();
            System.out.println();
        }

        System.out.println("=== Специфичные методы ===\n");
        System.out.println("У BMW " + bmw.getNumberOfDoors() + " дверей");
        volvo.loadCargo(15);
        System.out.println("Запас хода Tesla: " + tesla.getRange() + " км");
    }
}