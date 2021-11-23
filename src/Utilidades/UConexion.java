package Utilidades;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import java.util.ResourceBundle;

/*

La clase 'UConexion' tiene implementada el patrón de diseño 'Singleton'. Es la encargada de armar la conexión a base de 
datos. El Driver, la ubicación de la base de datos, el usuario y la contraseña se obtienen por medio del archivo llamado
'framework.properties', el cual puede modificarse externamente:

*/

public class UConexion
{
	private static UConexion instance;

	private Connection miConexion;

	public static UConexion getInstancia()
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

	public Connection getConnection()
	{
		return this.miConexion;
	}

	public void conectar()
	{
		try
		{
			ResourceBundle miResourceBundle = ResourceBundle.getBundle("framework");

			Class.forName(miResourceBundle.getString("Driver"));

			this.miConexion = DriverManager.getConnection(miResourceBundle.getString("Ubicación"), miResourceBundle.getString("Usuario"), miResourceBundle.getString("Contraseña"));
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
			this.miConexion.close();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
	}
}