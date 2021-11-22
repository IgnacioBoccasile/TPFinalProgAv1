package Servicios;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import Anotaciones.Columna;
import Anotaciones.Tabla;
import Anotaciones.Id;
import Utilidades.UBean;
import Utilidades.UConexion;

public class Consultas
{
	public static void guardar(Object o)
	{
		String consulta = "INSERT INTO ";

		int i  = 0;

		Class c = o.getClass();

		Tabla t = (Tabla) c.getAnnotation(Tabla.class);

		String nombreTabla = t.nombre();

		List<Field> atributos = UBean.obtenerAtributos(o);

		String columnas = " (";

		String valores = ") VALUES (";

		int k = 1;

		UConexion cone = UConexion.instancia();

		cone.conectar();

		Connection con = cone.getConexion();

		consulta = consulta + nombreTabla;

		for(Field f: atributos)
		{
			Columna col = f.getAnnotation(Columna.class);

			if(col != null)
			{
				columnas = columnas + col.nombre() + ",";

				i++;
			}
		}

		columnas = columnas.substring(0, columnas.length() - 1);

		for(int j = 0; j<i; j++)
		{
			valores = valores + "?,";
		}

		valores = valores.substring(0, valores.length() - 1);

		consulta = consulta + columnas + valores + ")";

		PreparedStatement ps;

		try
		{
			ps = con.prepareStatement(consulta);

			for(Field f: atributos)
			{
				try
				{
					if(f.getAnnotation(Columna.class) != null)
					{
						ps.setObject(k, UBean.ejecutarGet(o, f.getName()));

						k++;
					}
				}
				catch (SQLException e)
				{
					e.printStackTrace();
				}
			}

			ps.execute();
		}
		catch (SQLException e1)
		{
			e1.printStackTrace();
		}

		cone.desconectar();
	}

	public static void modificar(Object o)
	{
		String consulta = "UPDATE ";

		int i  = 0;

		Class c = o.getClass();

		Tabla t = (Tabla) c.getAnnotation(Tabla.class);

		String nombreTabla = t.nombre();

		List<Field> atributos = UBean.obtenerAtributos(o);

		String columnas = " ";

		String columnaId = "WHERE ";

		UConexion cone = UConexion.instancia();

		cone.conectar();

		Connection con = cone.getConexion();

		consulta = consulta + nombreTabla + " SET";

		for(Field f: atributos)
		{
			Id id = f.getAnnotation(Id.class);

			if(id != null)
			{
				System.out.println(f.getName()+ " 1");

				columnaId = columnaId + "id = " + UBean.ejecutarGet(o, f.getName());

				System.out.println(columnaId);
			}

			Columna col = f.getAnnotation(Columna.class);

			if(col != null)
			{
				columnas = columnas + col.nombre() + " = '" + UBean.ejecutarGet(o, f.getName()) + "' ,";

				i++;
			}
		}

		columnas = columnas.substring(0, columnas.length() - 1);

		consulta = consulta + columnas + columnaId;

		try
		{
			System.out.println(consulta);

			PreparedStatement st = con.prepareCall(consulta);

			st.execute();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}

		cone.desconectar();
	}

	public static void eliminar(Object o)
	{
		String consulta = "DELETE FROM ";

		Class c = o.getClass();

		Tabla t = (Tabla) c.getAnnotation(Tabla.class);

		String nombreTabla = t.nombre();

		List<Field> atributos = UBean.obtenerAtributos(o);

		String columnaId = "";

		consulta = consulta + nombreTabla + " WHERE ";

		UConexion cone = UConexion.instancia();

		cone.conectar();

		Connection con = cone.getConexion();

		for(Field f: atributos)
		{
			Id id = f.getAnnotation(Id.class);

			if(id != null)
			{
				columnaId = columnaId + "id = " + UBean.ejecutarGet(o, f.getName());
			}
		}

		consulta = consulta + columnaId;

		try
		{
			PreparedStatement st = con.prepareCall(consulta);

			st.execute();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}

		cone.desconectar();
	}

	public static Object obtenerPorId(Class c,Object id)
	{
		String consulta = "SELECT * FROM ";

		Tabla t = (Tabla) c.getAnnotation(Tabla.class);

		String nombreTabla = t.nombre();

		Object retorno = null;

		for(Constructor constru: c.getDeclaredConstructors())
		{
			if(constru.getParameterCount() == 0)
			{
				try
				{
					retorno = constru.newInstance();
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

		consulta = consulta + nombreTabla + " WHERE id = " + id;

		UConexion cone = UConexion.instancia();

		cone.conectar();

		Connection con = cone.getConexion();

		try
		{
			PreparedStatement select = con.prepareCall(consulta);

			ResultSet rs =  select.executeQuery();

			while(rs.next())
			{
				for(Field f: atributos)
				{
					//System.out.println(f.getName());

					//System.out.println(rs.getObject(f.getName()));

					UBean.ejecutarSet(retorno, f.getName(), rs.getObject(f.getName()));
				}
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}

		cone.desconectar();

		return retorno;
	}
}