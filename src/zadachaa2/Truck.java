package zadachaa2;


public class Truck extends Vehicle {
    private double loadCapacity;
    private int axles;

    public Truck(String brand, String model, int year, double price,
                 double loadCapacity, int axles) {
        super(brand, model, year, price);
        this.loadCapacity = loadCapacity;
        this.axles = axles;
    }

    public double getLoadCapacity() {
        return loadCapacity;
    }

    public int getAxles() {
        return axles;
    }

    @Override
    public void startEngine() {
        System.out.println(getBrand() + " " + getModel() +
                ": Грузовик запущен");
    }

    public void loadCargo(double weight) {
        if (weight <= loadCapacity) {
            System.out.println("Груз весом " + weight + " т загружен");
        } else {
            System.out.println("Ошибка: превышена грузоподъемность!");
        }
    }

    @Override
    public void displayInfo() {
        super.displayInfo();
        System.out.println("Грузоподъемность: " + loadCapacity + " т");
        System.out.println("Количество осей: " + axles);
    }
}