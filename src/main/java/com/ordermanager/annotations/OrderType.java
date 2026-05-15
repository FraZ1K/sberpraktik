package com.ordermanager.annotations;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Documented
public @interface OrderType {
    Type value() default Type.REGULAR;

    enum Type {
        URGENT("Срочный"),
        REGULAR("Обычный");

        private final String description;

        Type(String description) {
            this.description = description;
        }

        public String getDescription() {
            return description;
        }
    }
}