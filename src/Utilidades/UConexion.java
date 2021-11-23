package Utilidades;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class UConexion
{
	private static UConexion instance;

	private Connection conexion;

	public static UConexion instancia()
	{
		if(instance == null)
		{
			instance = new UConexion();

			return instance;
		}
		else
		{
			return instance;
		}
	}

	public Connection getConexion()
	{
		return this.conexion;
	}

	public void conectar()
	{
		try
		{
			ResourceBundle rs = ResourceBundle.getBundle("framework");

			Class.forName(rs.getString("Driver"));

			this.conexion = DriverManager.getConnection(rs.getString("Ubicación"), rs.getString("Usuario"),rs.getString("Contraseña"));
		}
		catch(ClassNotFoundException e)
		{
			e.printStackTrace();
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
	}

	public void desconectar()
	{
		try
		{
			this.conexion.close();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
	}
}