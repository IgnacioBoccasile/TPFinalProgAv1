package Main;

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

		System.out.println("1 - Alta de Persona");

		System.out.println("2 - Eliminar Persona");

		System.out.println("3 - Modificar Persona");

		System.out.println("4 - Consultar Persona\n");

		Scanner sc = new Scanner(System.in);

        String numero = sc.nextLine();

        switch(numero)
		{
			case "1":
				System.out.println("\nIngrese el Id:\n");

				id = Integer.parseInt(sc.nextLine());

				System.out.println("\nIngrese el Nombre:\n");

				nombre = sc.nextLine();

				System.out.println("\nIngrese el Apellido:\n");

				apellido = sc.nextLine();

				p = new Persona(id, nombre, apellido);

				Consultas.guardar(p);

				System.out.println("\n\nFELICITACIONES!!! La persona fue dada de alta exitosamente, ya puede actualizar la base de datos :)");

				break;

			case "2":
				System.out.println("\nIngrese el Id de la persona a dar de baja:\n");

				id = Integer.parseInt(sc.nextLine());

				p.setId(id);

				Consultas.eliminar(p);

				System.out.println("\n\nFELICITACIONES!!! La persona fue dada de baja exitosamente, ya puede actualizar la base de datos :)");
				
				break;

			case "3":
				System.out.println("\nIngrese el Id de la persona a modificar:\n");

				id = Integer.parseInt(sc.nextLine());

				System.out.println("\nIngrese el nombre:\n");

				nombre = sc.nextLine();

				System.out.println("\nIngrese el apellido:\n");

				apellido = sc.nextLine();

				p = new Persona(id, nombre, apellido);

				Consultas.modificar(p);

				break;

			case "4":
				System.out.println("Ingrese el id de la persona que desea obtener");

				id = Integer.parseInt(sc.nextLine());

				Persona o = (Persona) Consultas.obtenerPorId(Persona.class, id);

				System.out.println(o.toString());

				break;
		}
	}
}