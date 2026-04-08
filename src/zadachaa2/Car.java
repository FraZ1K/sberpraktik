package zadachaa2;

public class Car extends Vehicle {
    private int numberOfDoors;
    private String transmission;

    public Car(String brand, String model, int year, double price,
               int numberOfDoors, String transmission) {
        super(brand, model, year, price);
        this.numberOfDoors = numberOfDoors;
        this.transmission = transmission;
    }

    public int getNumberOfDoors() {
        return numberOfDoors;
    }

    public String getTransmission() {
        return transmission;
    }

    @Override
    public void startEngine() {
        System.out.println(getBrand() + " " + getModel() +
                ": Легковой автомобиль запущен");
    }

    @Override
    public void displayInfo() {
        super.displayInfo();
        System.out.println("Количество дверей: " + numberOfDoors);
        System.out.println("Коробка передач: " + transmission);
    }
}