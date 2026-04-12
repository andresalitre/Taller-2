package Taller2;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
		private static List<AltoMando> listaAltoMando = new ArrayList<>(); //Listas para objetos
		private static List<LiderGym> listaLiderGym = new ArrayList<>();
		private static List<Pokemon> Pokedex = new ArrayList<>();
		private static List<Habitat> Habitats = new ArrayList<>();
		private static Jugador jugador = new Jugador("","");
		static Boolean ejecutar = true;
		static Boolean cargaCompleta = false;
		
	public static void main(String[] args) throws IOException {
		establecerPokedex("Pokedex.txt");
		establecerAltoMando("Alto Mando.txt");
		establecerLideres("Gimnasios.txt");
		establecerZonas("Habitats.txt");
		
		int i = 0;
		for (Habitat p : Habitats) {
			p.habitantes();
		}
		
		menuInicial();
		
	}
	
	public static void menuInicial() throws IOException {
		Scanner sc = new Scanner(System.in);
		String opcion;
		do {
			System.out.print("1) Continuar.\n"+ "2) Nueva Partida.\n"+ "3) Salir.\n\nIngresar opción: ");
			opcion = sc.nextLine();
			
			switch (opcion) {
			case "1":
				cargarPartida();
				menuPartida();
				break;
			case "2":
				partidaNueva();
				menuPartida();
				break;
			case"3":
				ejecutar = false;
				System.out.println("Nos vemos entrenador...");
				break;
			default:
				System.out.println("\nOpción invalida.\n");
				break;
			}
			
		} while (ejecutar); sc.close();
		
	}
	
	public static void menuPartida() throws IOException {
		System.out.println("\nBienvenido " + jugador.getNombre()+ "\n");
		Scanner sc = new Scanner(System.in);
		String opcion;
		do {
			System.out.print("1) Revisar equipo.\r\n"
					+ "2) Salir a capturar.\r\n"
					+ "3) Acceso al PC (cambiar Pokémon del equipo).\r\n"
					+ "4) Retar un gimnasio.\r\n"
					+ "5) Desafío al Alto Mando.\r\n"
					+ "6) Curar Pokémon.\r\n"
					+ "7) Guardar.\r\n"
					+ "8) Guardar y Salir.\n\nSeleccionar opción: ");
			opcion = sc.nextLine();
			
			switch (opcion) {
			case "1":
				jugador.team();
				break;
			case "2":
				break;
			case "3":
				pc();
				break;
			case "6":
				curar();
				break;
			case"7":
				guardar();
				System.out.println("La partida ha sido guardada");
				break;
			case"8":
				ejecutar = false;
				guardar();
				System.out.println("Nos vemos entrenador...");
				return ;
			default:
				System.out.println("\nOpción invalida.\n");
				break;
			}
			
		} while (!opcion.equals("8")); sc.close();
	
	}
	
	
	public static void establecerAltoMando(String archivo) throws FileNotFoundException {
		Scanner lector = new Scanner(new File(archivo));
		while (lector.hasNextLine()) {
			 String linea = lector.nextLine();
			 String[] partes = linea.split(";");
			 AltoMando persona = new AltoMando(Integer.valueOf(partes[0]), partes[1]);
			 listaAltoMando.add(persona); // Por cada linea creamos un alto mando
			 for (int i = 2; i < 8; i++) { //Para revisar cada pokemon tipo (String de la linea)
				 for (Pokemon pokemon : Pokedex) { //Se revisa cada Nombre de los pokemon en la pokedex
					 if (partes[i].equals(pokemon.getNombre())) { //Se revisa si es igual el pokemon de la pokedex con el del alto mando
						 persona.obtenerPokemon(pokemon); //Si son iguales se le añade
					 }
				 }
			 }
		} lector.close();
	}
	
	public static void establecerPokedex(String archivo) throws FileNotFoundException {
		Scanner lector = new Scanner(new File(archivo));
		while (lector.hasNextLine()) {
			 String linea = lector.nextLine();
			 String[] partes = linea.split(";");
			 Pokemon poke = new Pokemon(partes[0],partes[1], Double.valueOf(partes[2]),
						Integer.valueOf(partes[3]),Integer.valueOf(partes[4]),Integer.valueOf(partes[5]),
						Integer.valueOf(partes[6]),Integer.valueOf(partes[7]),Integer.valueOf(partes[8]), partes[9]);
			 Pokedex.add(poke);
			 //Se añaden todos los pokemon del archivo a la pokedex
		} lector.close();
	}
	
	public static void establecerLideres(String archivo) throws FileNotFoundException {
		Scanner lector = new Scanner(new File(archivo));
		while (lector.hasNextLine()) {
			 String linea = lector.nextLine();
			 String[] partes = linea.split(";");
			 LiderGym persona = new LiderGym(Integer.valueOf(partes[0]), partes[1], partes[2]);
			 listaLiderGym.add(persona); //Se crea un lider por cada linea
			 for (int i = 3; i < 4 + Integer.valueOf(partes[3]); i++) { 
				 // dependiendo de la cantidad de pokes del lider, se revisa los pokemon tipo String de la linea
				 for (Pokemon pokemon : Pokedex) { //Se revisa toda la pokedex
					 if (partes[i].equals(pokemon.getNombre())) { //Se revisa que coincidan los pokes
						 persona.obtenerPokemon(pokemon); //Se añade el pokemon al Lider si coinciden
					 }
				 }
			 }
		} lector.close();
	}
	
	public static void establecerZonas(String archivo) throws FileNotFoundException {
		Scanner lector = new Scanner(new File(archivo));
		while (lector.hasNextLine()) {
			 String linea = lector.nextLine();
			 Habitat h = new Habitat(linea);
			 Habitats.add(h);
			 for (Pokemon p : Pokedex) {
				 if (linea.equals(p.getHabitad())) {
					 h.agregarPokemon(p);
				 }
			 }
		}
	}
	
	public static void pc() {
		
	}
	
	public static void capturar() {
		Scanner sc = new Scanner(System.in);
		String opcion;
		do {
			opcion = sc.nextLine();
			System.out.println("Donde deseas ir a explorar?\r\n"
				+ "\r\n"
				+ "Zonas disponibles:\r\n"
				+ "\r\n"
				+ "1) Lago\r\n"
				+ "2) Cueva\r\n"
				+ "3) Montaña\r\n"
				+ "4) Bosque\r\n"
				+ "5) Prado\r\n"
				+ "6) Mar\r\n"
				+ "7) Volver al menu.");	
			switch (opcion) {
			case "1":
				
			}
			
		} while (!opcion.equals("7"));
	}
	
	public static void curar() { //se revisan los objetos pokemonjugador como p, desde el equipo del jugador
		for (PokemonJugador p : jugador.getEquipo()) {
			if (p.getEstado().equals("Debilitado")) { //si su estado es muerto se hace vivo
				p.setEstado("Vivo");
			}
		}
	}
	
	public static void guardar() throws IOException {
		try (BufferedWriter reescritor = new BufferedWriter(new FileWriter("Registros.txt"))) {
			reescritor.write(jugador.getNombre()+";"+jugador.getMedallas());
			reescritor.newLine(); //escribir el nombre;medellas
			for (PokemonJugador p : jugador.getEquipo()) {
				reescritor.write(p.getPokemon().getNombre()+";"+p.getEstado());
				reescritor.newLine(); //se recorren todo el equipo y se escribe pokemon;estado
			}
		}
	}
	
	 public static void partidaNueva() throws IOException {
		 Scanner sc = new Scanner(System.in);
			System.out.print("\nIngrese su apodo de jugador: ");
			String apodo = sc.nextLine();
		 try (BufferedWriter reescritor = new BufferedWriter(new FileWriter("Registros.txt"))) {
			 reescritor.write(apodo+";"+"none");
			 jugador.setNombre(apodo);
			 jugador.setMedallas("none");
		 }
	 }
	 
	 public static void cargarPartida() throws IOException {
		 Scanner lector = new Scanner(new File("Registros.txt"));
		 
		 int primeraLinea = 0;
		 while (lector.hasNextLine()) {
			 String linea = lector.nextLine();
			 String[] partes = linea.split(";");
			 if (primeraLinea == 0) {
			 jugador.setNombre(partes[0]);
			 jugador.setMedallas(partes[1]);
			 primeraLinea = 1;
			 }
			 for (Pokemon pokemon : Pokedex) { 
				if (partes[0].equals(pokemon.getNombre())) { 
					if (partes[1].equals("Debilitado")) {
						jugador.obtenerPokemon(pokemon,"Debilitado");
					} else jugador.obtenerPokemon(pokemon,"Vivo");
				
				}
			}
		}
	}
}

