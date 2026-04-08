package zadachaa2;

public class Vehicle {
    private String brand;
    private String model;
    private int year;
    private double price;

    public Vehicle(String brand, String model, int year, double price) {
        this.brand = brand;
        this.model = model;
        this.year = year;
        this.price = price;
    }

    public String getBrand() {
        return brand;
    }

    public String getModel() {
        return model;
    }

    public int getYear() {
        return year;
    }

    public double getPrice() {
        return price;
    }

    public void startEngine() {
        System.out.println(brand + " " + model + ": Двигатель запущен");
    }

    public void stopEngine() {
        System.out.println(brand + " " + model + ": Двигатель остановлен");
    }

    public void displayInfo() {
        System.out.println("Марка: " + brand);
        System.out.println("Модель: " + model);
        System.out.println("Год: " + year);
        System.out.println("Цена: " + price + " руб.");
    }
}