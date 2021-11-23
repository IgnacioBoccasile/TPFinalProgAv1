package Utilidades;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import java.util.ArrayList;

public class UBean
{

/*

El método 'obtenerAtributos' devuelve un ArrayList<Field> con todos los atributos que posee el parámetro Object:

*/

	public static ArrayList<Field> obtenerAtributos(Object o)
	{
		ArrayList<Field> atributos = new ArrayList<Field>();

		Class miClase = o.getClass();

		for(Field f : miClase.getDeclaredFields())
		{
			atributos.add(f);
		}

		return atributos;
	}

/*

El método 'ejecutarSet' ejecuta el Setter del String dentro del Object:

*/

	public static void ejecutarSet(Object o, String att, Object valor)
	{
		if(o != null && valor != null)
		{
			Class miClase = o.getClass();

			String miAtributo = att.substring(0, 1).toUpperCase() + att.substring(1);

			for(Method miMethod : miClase.getDeclaredMethods())
			{
				if(miMethod.getName().startsWith("set".concat(miAtributo)))
				{
					try
					{
						miMethod.invoke(o, valor);
					}
					catch (IllegalAccessException e)
					{
						e.printStackTrace();
					}
					catch (IllegalArgumentException e)
					{
						e.printStackTrace();
					}
					catch (InvocationTargetException e)
					{
						e.printStackTrace();
					}
				}
			}
		}
	}

/*

El método 'ejecutarGet' devuelve el valor del atributo pasado por parámetro, ejecutando el getter dentro del objeto:

*/

	public static Object ejecutarGet(Object o, String att)
	{
		Object retorno = null;

		if(o != null)
		{
			Class miClase = o.getClass();

			String miAtributo = att.substring(0, 1).toUpperCase() + att.substring(1);

			for(Method miMethod : miClase.getDeclaredMethods())
			{
				if(miMethod.getName().startsWith("get".concat(miAtributo)))
				{
					try
					{
						retorno = miMethod.invoke(o);
					}
					catch (IllegalAccessException e)
					{
						e.printStackTrace();
					}
					catch (IllegalArgumentException e)
					{
						e.printStackTrace();
					}
					catch (InvocationTargetException e)
					{
						e.printStackTrace();
					}
				}
			}
		}

		return retorno;
	}
}