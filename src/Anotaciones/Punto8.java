/*

8. Generar una nueva anotación a nivel de atributo, la cual va a permitir guardar en cascada dos objetos. Por ejemplo, si la 
clase 'Persona' tiene el atributo 'Domicilio', al guardar persona se debe guardar también el domicilio.

*/

package Anotaciones;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.annotation.ElementType;

@Retention(RetentionPolicy.RUNTIME)

@Target(ElementType.FIELD)

public @interface Punto8
{
    Class clase();
}