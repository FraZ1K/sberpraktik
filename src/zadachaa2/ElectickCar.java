package zadachaa2;

public class ElectickCar extends Car implements ElectrickVehicle {
    private int batteryLevel;
    private int range;

    public ElectickCar(String brand, String model, int year, double price,
                       int numberOfDoors, String transmission, int range) {
        super(brand, model, year, price, numberOfDoors, transmission);
        this.batteryLevel = 100;
        this.range = range;
    }

    @Override
    public void chargeBattery() {
        batteryLevel = 100;
        System.out.println(getBrand() + " " + getModel() + ": Аккумулятор заряжен");
    }

    @Override
    public int getBatteryLevel() {
        return batteryLevel;
    }

    @Override
    public int getRange() {
        return range;
    }

    @Override
    public void startEngine() {
        if (batteryLevel > 0) {
            System.out.println(getBrand() + " " + getModel() + ": Электромобиль запущен");
        } else {

            System.out.println(getBrand() + " " + getModel() + ": Батарея разряжена");
        }
    }

    public void drive(int km) {
        if (km <= range) {
            int used = (km * 100) / range;
            batteryLevel -= used;
            System.out.println("Проехали " + km + " км, заряд: " + batteryLevel + "%");
        } else {
            System.out.println("Недостаточно заряда");
        }
    }

    @Override
    public void displayInfo() {
        super.displayInfo();
        System.out.println("Тип: Электромобиль");
        System.out.println("Запас хода: " + range + " км");
        System.out.println("Заряд: " + batteryLevel + "%");
    }
}