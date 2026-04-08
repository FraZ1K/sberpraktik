package zadachaa1;

import java.io.Serializable;


public class zadanie3 implements Serializable {

    private int denomination;

    private int quantity;

    public zadanie3(int denomination, int quantity) {
        if (denomination <= 0) {
            throw new IllegalArgumentException("Номинал должен быть положительным числом");
        }
        if (quantity < 0) {
            throw new IllegalArgumentException("Количество не может быть отрицательным");
        }
        this.denomination = denomination;
        this.quantity = quantity;
    }


    public String getInfo() {
        return String.format("Номинал: %d руб. | Количество: %d шт. | Сумма: %d руб.",
                denomination, quantity, calculateTotal());
    }


    public int calculateTotal() {
        return denomination * quantity;
    }

    public int getDenomination() {
        return denomination;
    }

    public void setDenomination(int denomination) {
        if (denomination <= 0) {
            throw new IllegalArgumentException("Номинал должен быть положительным");
        }
        this.denomination = denomination;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        if (quantity < 0) {
            throw new IllegalArgumentException("Количество не может быть отрицательным");
        }
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return getInfo();
    }
}