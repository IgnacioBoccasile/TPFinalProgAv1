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

		Persona miPersona = new Persona();

		System.out.println("\nBIENVENIDO!!!\n\n\nIngrese el número de operación:\n");

		System.out.println("1 - Cargar Persona");

		System.out.println("2 - Eliminar Persona");

		System.out.println("3 - Modificar Persona");

		System.out.println("4 - Consultar Persona");

		System.out.println("5 - Probar 'guardarModificar'");

		System.out.println("6 - Mostrar Personas\n");

		Scanner miScanner = new Scanner(System.in);

        String num = miScanner.nextLine();

        switch(num)
		{
			case "1":
				System.out.println("\nIngrese el ID:\n");

				id = Integer.parseInt(miScanner.nextLine());

				System.out.println("\nIngrese el Nombre:\n");

				nombre = miScanner.nextLine();

				System.out.println("\nIngrese el Apellido:\n");

				apellido = miScanner.nextLine();

				miPersona = new Persona(id, nombre, apellido);

				Consultas.guardar(miPersona, id);

				System.out.println(miPersona.toStringGuardar());

				break;


			case "2":
				System.out.println("\nIngrese el ID de la Persona a dar de baja:\n");

				id = Integer.parseInt(miScanner.nextLine());

				miPersona.setId(id);

				Consultas.eliminar(miPersona);

				break;


			case "3":
				System.out.println("\nIngrese el ID de la Persona a modificar:\n");

				id = Integer.parseInt(miScanner.nextLine());

				System.out.println("\nIngrese el nuevo Nombre:\n");

				nombre = miScanner.nextLine();

				System.out.println("\nIngrese el nuevo Apellido:\n");

				apellido = miScanner.nextLine();

				miPersona = new Persona(id, nombre, apellido);

				Consultas.modificar(miPersona);

				break;


			case "4":
				System.out.println("\nIngrese el ID de la Persona que desea consultar:\n");

				id = Integer.parseInt(miScanner.nextLine());

				Persona miObjeto = (Persona) Consultas.obtenerPorId(Persona.class, id);


				if(miObjeto.getId() == null)
				{
					System.out.println("\nLo sentimos, no existe una Persona con ese ID en la base de datos :(");
				}
				else
				{
					System.out.println(miObjeto.toStringConsultar());
				}

				break;


			case "5":
				System.out.println("\nIngrese el ID de la Persona a modificar:\n");

				id = Integer.parseInt(miScanner.nextLine());

				System.out.println("\nIngrese el nuevo Nombre:\n");

				nombre = miScanner.nextLine();

				System.out.println("\nIngrese el nuevo Apellido:\n");

				apellido = miScanner.nextLine();

				miPersona = new Persona(id, nombre, apellido);

				Consultas.guardarModificar(miPersona, id);

				break;


			case "6":
				ArrayList<Object> personas = Consultas.obtenerTodos(Persona.class);

				System.out.println("\nEstas son todas las personas que se encuentran cargadas en la base de datos:\n");

				for(Object o : personas)
				{
					miPersona = (Persona) o;

					System.out.println(miPersona.toStringObtenerTodos());
				}

				break;
		}
	}
}