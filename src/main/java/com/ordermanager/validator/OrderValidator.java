package com.ordermanager.validator;

import com.ordermanager.annotations.Validate;
import com.ordermanager.model.Order;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class OrderValidator {

    public List<String> validate(Order order) {
        List<String> errors = new ArrayList<>();

        if (order == null) {
            errors.add("Заказ не может быть null");
            return errors;
        }

        Field[] fields = Order.class.getDeclaredFields();

        for (Field field : fields) {
            if (field.isAnnotationPresent(Validate.class)) {
                field.setAccessible(true);
                Validate validate = field.getAnnotation(Validate.class);

                try {
                    Object value = field.get(order);

                    if (validate.required() && value == null) {
                        errors.add(validate.message());
                        continue;
                    }

                    if (value != null) {
                        if (value instanceof String) {
                            String strValue = (String) value;
                            if (strValue.length() < validate.minLength()) {
                                errors.add(String.format("Поле '%s': длина меньше минимальной (%d)",
                                        field.getName(), validate.minLength()));
                            }
                            if (strValue.length() > validate.maxLength()) {
                                errors.add(String.format("Поле '%s': длина больше максимальной (%d)",
                                        field.getName(), validate.maxLength()));
                            }
                        }

                        if (value instanceof Number) {
                            int intValue = ((Number) value).intValue();
                            if (intValue < validate.min()) {
                                errors.add(String.format("Поле '%s': значение меньше минимального (%d)",
                                        field.getName(), validate.min()));
                            }
                            if (intValue > validate.max()) {
                                errors.add(String.format("Поле '%s': значение больше максимального (%d)",
                                        field.getName(), validate.max()));
                            }
                        }
                    }

                } catch (IllegalAccessException e) {
                    errors.add("Ошибка доступа к полю: " + field.getName());
                }
            }
        }

        return errors;
    }

    public void validateOrThrow(Order order) throws ValidationException {
        List<String> errors = validate(order);
        if (!errors.isEmpty()) {
            throw new ValidationException("Заказ не прошел валидацию: " + String.join("; ", errors));
        }
    }

    public static class ValidationException extends Exception {
        public ValidationException(String message) {
            super(message);
        }
    }
}