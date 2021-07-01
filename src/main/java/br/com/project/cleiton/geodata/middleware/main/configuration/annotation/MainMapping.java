package br.com.project.cleiton.geodata.middleware.main.configuration.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)

public @interface MainMapping {
    public String endpoint() default "";

    public String type() default "";
}
