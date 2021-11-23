package Anotaciones;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.annotation.ElementType;

@Retention(RetentionPolicy.RUNTIME)

//Anotación declarada a nivel de clase, y permite ingresar la propiedad 'nombre':

@Target(ElementType.TYPE)

public @interface Tabla
{
    String nombre();
}