package Main;

import java.util.ArrayList;
import java.util.Scanner;
import Servicios.Consultas;

public class Program
{
	public static void main(String[] args)
	{
		Integer id;

		String nombre;

		String apellido;

		Persona p = new Persona();

		System.out.println("\nBIENVENIDO!!!\n\n\nIngrese el número de operación:\n");

		System.out.println("1 - Cargar Persona");

		System.out.println("2 - Eliminar Persona");

		System.out.println("3 - Modificar Persona");

		System.out.println("4 - Consultar Datos Persona");
		
		System.out.println("5 - Probar Método 'guardarModificar'");
		
		System.out.println("6 - Obtener Todas las Personas\n");

		Scanner sc = new Scanner(System.in);

        String numero = sc.nextLine();

        switch(numero)
		{
			case "1":

				System.out.println("\nIngrese el Id:\n");

				id = Integer.parseInt(sc.nextLine());

				System.out.println("\nIngrese el nombre:\n");

				nombre = sc.nextLine();

				System.out.println("\nIngrese el apellido:\n");

				apellido = sc.nextLine();

				p = new Persona(id, nombre, apellido);

				Consultas.guardar(p, id);

				System.out.println(p.toStringGuardar());
				
				break;

			case "2":

				System.out.println("\nIngrese el Id de la persona a dar de baja:\n");

				id = Integer.parseInt(sc.nextLine());
				
				p.setId(id);

				Consultas.eliminar(p);
				
				break;

			case "3":

				System.out.println("\nIngrese el Id de la persona a modificar:\n");

				id = Integer.parseInt(sc.nextLine());

				System.out.println("\nIngrese el nuevo nombre:\n");

				nombre = sc.nextLine();

				System.out.println("\nIngrese el nuevo apellido:\n");

				apellido = sc.nextLine();

				p = new Persona(id, nombre, apellido);

				Consultas.modificar(p);

				break;

			case "4":
				System.out.println("\nIngrese el Id de la persona que desea consultar:\n");

				id = Integer.parseInt(sc.nextLine());

				Persona o = (Persona) Consultas.obtenerPorId(Persona.class, id);

				
				if(o.getId() == null)
				{
					System.out.println("\nLo sentimos, no existe una persona con ese Id en la base de datos :(");
				}
				
				else
				{
					System.out.println(o.toStringConsultar());
				}
				
				break;
				
			case "5":

				System.out.println("\nIngrese el Id de la persona a modificar:\n");

				id = Integer.parseInt(sc.nextLine());

				System.out.println("\nIngrese el nuevo nombre:\n");

				nombre = sc.nextLine();

				System.out.println("\nIngrese el nuevo apellido:\n");

				apellido = sc.nextLine();

				p = new Persona(id, nombre, apellido);

				Consultas.guardarModificar(p, id);

				break;
				
			case "6":
				
				ArrayList<Object> array = Consultas.obtenerTodos(Persona.class);
				
				System.out.println("\nEstas son todas las personas que se encuentran cargadas en la base de datos:\n");
				
				for(Object obj:array) 
				{
					p = (Persona) obj;
					
					System.out.println(p.toStringObtenerTodos());
				}

				/*ArrayList<Object> list = Consultas.obtenerTodos(Persona.class);
				
				System.out.println("\nEstas son todas las personas que se encuentran cargadas en la base de datos:\n");
				
				for(Object ob : list)
				{
					p = (Persona) ob;
					
					System.out.println(p.toStringObtenerTodos());
				}

				break;*/
		}
	}
}