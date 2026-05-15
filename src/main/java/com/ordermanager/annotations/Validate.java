package com.ordermanager.annotations;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Documented
public @interface Validate {
    String message() default "Поле не прошло валидацию";
    boolean required() default true;
    int min() default Integer.MIN_VALUE;
    int max() default Integer.MAX_VALUE;
    int minLength() default 0;
    int maxLength() default Integer.MAX_VALUE;
}