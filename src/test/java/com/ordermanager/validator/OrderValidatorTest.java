package com.ordermanager.validator;

import com.ordermanager.annotations.OrderType;
import com.ordermanager.model.Order;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class OrderValidatorTest {

    private OrderValidator validator;
    private Order validOrder;

    @BeforeEach
    void setUp() {
        validator = new OrderValidator();
        validOrder = new Order("Валидный заказ", 50, OrderType.Type.REGULAR);
    }

    @Test
    void testValidOrder() throws Exception {
        assertDoesNotThrow(() -> validator.validateOrThrow(validOrder));
    }

    @Test
    void testNullId() {
        validOrder.setId(null);
        assertThrows(Exception.class, () -> validator.validateOrThrow(validOrder));
    }

    @Test
    void testEmptyDescription() {
        validOrder.setDescription("");
        assertThrows(Exception.class, () -> validator.validateOrThrow(validOrder));
    }

    @Test
    void testQuantityTooLow() {
        validOrder.setQuantity(0);
        assertThrows(Exception.class, () -> validator.validateOrThrow(validOrder));
    }

    @Test
    void testQuantityTooHigh() {
        validOrder.setQuantity(1001);
        assertThrows(Exception.class, () -> validator.validateOrThrow(validOrder));
    }

    @Test
    void testValidateMethodReturnsErrors() {
        Order invalidOrder = new Order();
        invalidOrder.setId(null);
        invalidOrder.setDescription("");
        invalidOrder.setQuantity(0);

        List<String> errors = validator.validate(invalidOrder);
        assertFalse(errors.isEmpty());
        assertTrue(errors.size() >= 3);
    }

    @Test
    void testValidateNullOrder() {
        List<String> errors = validator.validate(null);
        assertFalse(errors.isEmpty());
        assertEquals("Заказ не может быть null", errors.get(0));
    }

    @Test
    void testDescriptionBoundaryValues() throws Exception {
        Order order = new Order();
        order.setId("123");
        order.setQuantity(50);
        order.setOrderType(OrderType.Type.REGULAR);

        // Минимальная длина 1
        order.setDescription("A");
        assertDoesNotThrow(() -> validator.validateOrThrow(order));

        // Максимальная длина 200
        order.setDescription("A".repeat(200));
        assertDoesNotThrow(() -> validator.validateOrThrow(order));

        // Слишком длинное
        order.setDescription("A".repeat(201));
        assertThrows(Exception.class, () -> validator.validateOrThrow(order));
    }
}