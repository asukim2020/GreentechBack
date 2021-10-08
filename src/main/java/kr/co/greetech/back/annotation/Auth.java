package kr.co.greetech.back.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Auth {

    Role role() default Role.LOGIN;

    enum Role {
        NONE,
        LOGIN,
        COMPANY,
        DATA_LOGGER,
    }
}
