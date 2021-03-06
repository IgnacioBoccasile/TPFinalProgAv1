package Anotaciones;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.annotation.ElementType;

@Retention(RetentionPolicy.RUNTIME)

//Anotación declarada a nivel de atributo:

@Target(ElementType.FIELD)

public @interface Id
{
}