package Servicios;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;

import Anotaciones.Columna;
import Anotaciones.Tabla;
import Anotaciones.Id;

import Utilidades.UBean;
import Utilidades.UConexion;

public class Consultas
{

/*

El método 'guardar' guarda en la base de datos el objeto. La query se arma por medio de reflexión, utilizando tanto las 
annotations del package 'Anotaciones', como los métodos de 'UBean' en el package 'Utilidades'. También se modificó para que,
además de guardar, retorne el objeto con el atributo "ID" generado por la base de datos:

*/

	public static Object guardar(Object o, Object idConsultar)
	{
		String query = "INSERT INTO ";

		Class miClase = o.getClass();

		Tabla tabla = (Tabla) miClase.getAnnotation(Tabla.class);

		String nombreTabla = tabla.nombre();

		List<Field> atributos = UBean.obtenerAtributos(o);

		String columnas = " (";

		String valores = ") VALUES (";

		UConexion miUConexion = UConexion.getInstancia();

		miUConexion.conectar();

		Connection miConnection = miUConexion.getConnection();

		query = query + nombreTabla;

		int i  = 0;

		for(Field f : atributos)
		{
			Columna miColumna = f.getAnnotation(Columna.class);

			if(miColumna != null)
			{
				columnas = columnas + miColumna.nombre() + ",";

				i++;
			}
		}

		columnas = columnas.substring(0, columnas.length() - 1);

		for(int j = 0; j < i; j++)
		{
			valores = valores + "?,";
		}

		valores = valores.substring(0, valores.length() - 1);

		query = query + columnas + valores + ")";

		PreparedStatement miPreparedStatement;

		int k = 1;

		try
		{
			miPreparedStatement = miConnection.prepareStatement(query);

			for(Field f : atributos)
			{
				try
				{
					if(f.getAnnotation(Columna.class) != null)
					{
						miPreparedStatement.setObject(k, UBean.ejecutarGet(o, f.getName()));

						k++;
					}
				}
				catch (SQLException e)
				{
					e.printStackTrace();
				}
			}

			miPreparedStatement.execute();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}

		miUConexion.desconectar();

		return obtenerPorId(miClase, idConsultar);
	}

/*

El método 'modificar' modifica todas las columnas, excepto 'ID', la cual se utiliza para la restricción 'WHERE'. La query se
arma por medio de reflexión, utilizando tanto las annotations del package 'Anotaciones', como los métodos de 'UBean' en el
package 'Utilidades':

*/

	public static void modificar(Object o)
	{
		String query = "UPDATE ";

		Class miClase = o.getClass();

		Tabla tabla = (Tabla) miClase.getAnnotation(Tabla.class);

		String nombreTabla = tabla.nombre();

		List<Field> atributos = UBean.obtenerAtributos(o);

		String columnas = " ";

		String columnaId = "WHERE ";

		UConexion miUConexion = UConexion.getInstancia();

		miUConexion.conectar();

		Connection miConnection = miUConexion.getConnection();

		query = query + nombreTabla + " SET";

		int i  = 0;

		for(Field f : atributos)
		{
			Id id = f.getAnnotation(Id.class);

			if(id != null)
			{
				columnaId = columnaId + "id = " + UBean.ejecutarGet(o, f.getName());
			}

			Columna miColumna = f.getAnnotation(Columna.class);

			if(miColumna != null)
			{
				columnas = columnas + miColumna.nombre() + " = '" + UBean.ejecutarGet(o, f.getName()) + "' ,";

				i++;
			}
		}

		columnas = columnas.substring(0, columnas.length() - 1);

		query = query + columnas + columnaId;

		try
		{
			PreparedStatement miPreparedStatement = miConnection.prepareCall(query);

			if(miPreparedStatement.executeUpdate() != 0)
			{
				System.out.println("\nFELICITACIONES!!! La Persona fue modificada exitosamente, ya puede verificarlo en la base de datos :)");
			}
			else
			{
				System.out.println("\nLo sentimos, no existe una Persona con ese ID en la base de datos :(");
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}

		miUConexion.desconectar();
	}

/*

El método 'eliminar' elimina el registro de la base de datos. La query se arma por medio de reflexión, utilizando tanto las 
annotations del package 'Anotaciones', como los métodos de 'UBean' en el package 'Utilidades':

*/

	public static void eliminar(Object o)
	{
		String query = "DELETE FROM ";

		Class miClase = o.getClass();

		Tabla tabla = (Tabla) miClase.getAnnotation(Tabla.class);

		String nombreTabla = tabla.nombre();

		List<Field> atributos = UBean.obtenerAtributos(o);

		String columnaId = "";

		query = query + nombreTabla + " WHERE ";

		UConexion miUConexion = UConexion.getInstancia();

		miUConexion.conectar();

		Connection miConnection = miUConexion.getConnection();

		for(Field f : atributos)
		{
			Id id = f.getAnnotation(Id.class);

			if(id != null)
			{
				columnaId = columnaId + "id = " + UBean.ejecutarGet(o, f.getName());
			}
		}

		query = query + columnaId;

		try
		{
			PreparedStatement miPreparedStatement = miConnection.prepareCall(query);

			if(miPreparedStatement.executeUpdate() != 0)
			{
				System.out.println("\nFELICITACIONES!!! La Persona fue dada de baja exitosamente, ya puede verificarlo en la base de datos :)");
			}
			else
			{
				System.out.println("\nLo sentimos, no existe una Persona con ese ID en la base de datos :(");
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}

		miUConexion.desconectar();
	}

/*

El método 'obtenerPorId' devuelve un objeto del tipo definido en el parámetro 'Class', con todos sus datos cargados. La
query se arma por medio de reflexión, utilizando tanto las annotations del package 'Anotaciones', como los métodos de 
'UBean' en el package 'Utilidades':

*/

	public static Object obtenerPorId(Class c, Object id)
	{
		String query = "SELECT * FROM ";

		Tabla tabla = (Tabla) c.getAnnotation(Tabla.class);

		String nombreTabla = tabla.nombre();

		Object retorno = null;

		for(Constructor miConstructor : c.getDeclaredConstructors())
		{
			if(miConstructor.getParameterCount() == 0)
			{
				try
				{
					retorno = miConstructor.newInstance();
				}
				catch (InstantiationException e)
				{
					e.printStackTrace();
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

		List<Field> atributos = UBean.obtenerAtributos(retorno);

		query = query + nombreTabla + " WHERE id = " + id;

		UConexion miUConexion = UConexion.getInstancia();

		miUConexion.conectar();

		Connection miConnection = miUConexion.getConnection();

		try
		{
			PreparedStatement miPreparedStatement = miConnection.prepareCall(query);

			ResultSet miResultSet =  miPreparedStatement.executeQuery();

			while(miResultSet.next())
			{
				for(Field f : atributos)
				{
					UBean.ejecutarSet(retorno, f.getName(), miResultSet.getObject(f.getName()));
				}
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}

		miUConexion.desconectar();

		return retorno;
	}

/*

El método 'guardarModificar' guarda el objeto si es que el mismo no existe en la base de datos. Pero lo modifica en caso
de que ya se encuentre persistido:

*/

	public static void guardarModificar(Object o, Object idGuardarModificar)
	{
		String query = "UPDATE ";

		Class miClase = o.getClass();

		Tabla tabla = (Tabla) miClase.getAnnotation(Tabla.class);

		String nombreTabla = tabla.nombre();

		List<Field> atributos = UBean.obtenerAtributos(o);

		String columnas = " ";

		String columnaId = "WHERE ";

		UConexion miUConexion = UConexion.getInstancia();

		miUConexion.conectar();

		Connection miConnection = miUConexion.getConnection();

		query = query + nombreTabla + " SET";

		int i  = 0;

		for(Field f : atributos)
		{
			Id id = f.getAnnotation(Id.class);

			if(id != null)
			{
				columnaId = columnaId + "id = " + UBean.ejecutarGet(o, f.getName());
			}

			Columna miColumna = f.getAnnotation(Columna.class);

			if(miColumna != null)
			{
				columnas = columnas + miColumna.nombre() + " = '" + UBean.ejecutarGet(o, f.getName()) + "' ,";

				i++;
			}
		}

		columnas = columnas.substring(0, columnas.length() - 1);

		query = query + columnas + columnaId;

		try
		{
			PreparedStatement miPreparedStatement = miConnection.prepareCall(query);

			if(miPreparedStatement.executeUpdate() != 0)
			{
				System.out.println("\nFELICITACIONES!!! La Persona fue modificada exitosamente, ya puede verificarlo en la base de datos :)");
			}
			else
			{
				System.out.println("\nLa Persona indicada no existía en la base de datos, así que la agregamos. Ya puede verificarlo :)");

				guardar(o, idGuardarModificar);
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}

		miUConexion.desconectar();
	}

/*

El método 'obtenerTodos' obtiene todos los registros de la base de datos.

*/

	public static ArrayList<Object> obtenerTodos(Class c)
	{
		String query = "SELECT * FROM ";

		String nombreTabla = ((Tabla)c.getAnnotation(Tabla.class)).nombre();

		query = query + nombreTabla;

		ArrayList<Object> personas = new ArrayList<Object>();	

		try
		{
			UConexion miUConexion = UConexion.getInstancia();

			miUConexion.conectar();

			Connection miConnection = miUConexion.getConnection();

			PreparedStatement miPreparedStatement = miConnection.prepareStatement(query);

			ResultSet miResultSet = miPreparedStatement.executeQuery();

			while(miResultSet.next())
			{
				Object miObjeto = null;

				for(Constructor miConstructor : c.getConstructors())
				{
					if(miConstructor.getParameterCount() == 0)
					{
						miObjeto = miConstructor.newInstance(null);

						break;
					}
				}

				ArrayList<Field> atributos = UBean.obtenerAtributos(miObjeto);

				for (int i = 0; i < atributos.size(); i++)
				{
					  Columna miColumna = atributos.get(i).getAnnotation(Columna.class);

					  if(miColumna!=null)
					  {
						  UBean.ejecutarSet(miObjeto, atributos.get(i).getName(), miResultSet.getObject(miColumna.nombre(), atributos.get(i).getType()));
					  }
				}

				personas.add(miObjeto);
			}
		} 
		catch (SQLException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) 
		{
			e.printStackTrace();
		}
		
		return personas;
	}
}