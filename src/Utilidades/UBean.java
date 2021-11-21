package Utilidades;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

public class UBean
{
    public static ArrayList<Field> obtenerAtributos(Object o)
    {
        ArrayList<Field> misAtributos = new ArrayList<Field>();

        Field[] atributos = o.getClass().getDeclaredFields();

        for (Field a : atributos)
        {
            misAtributos.add(a);
        }
        
        return misAtributos;
    }

    public static Object ejecutarSet(Object o, String att, Object valor)
    {
        Method[] misMethods = o.getClass().getDeclaredMethods();
        
        String atributo = att.substring(0, 1).toUpperCase() + att.substring(1);

        try
        {
            for (Method m: misMethods)
            {
                if(m.getName().startsWith("set" + atributo))
                {
                    m.invoke(o, valor);
                }
            }
        }
        catch (InvocationTargetException e)
        {
            e.printStackTrace();
        }
        catch (IllegalAccessException e)
        {
            e.printStackTrace();
        }
        
        return o;
    }

    public static Object ejecutarGet(Object o, String att)
    {
        Object valor = new Object();
        
        Method[] misMethods = o.getClass().getDeclaredMethods();
        
        String atributo = att.substring(0, 1).toUpperCase() + att.substring(1);

        try
        {
            for (Method m: misMethods)
            {
                if(m.getName().startsWith("get" + atributo))
                {
                    valor = m.invoke(o);
                }
            }
        }
        catch (IllegalAccessException e)
        {
            e.printStackTrace();
        }
        catch (InvocationTargetException e)
        {
            e.printStackTrace();
        }
        
        return valor;
    }
}