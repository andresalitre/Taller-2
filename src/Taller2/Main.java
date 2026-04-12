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
		private static Jugador jugador = new Jugador("","");
		static Boolean ejecutar = true;
		static Boolean cargaCompleta = false;
		
	public static void main(String[] args) throws IOException {
		establecerPokedex("Pokedex.txt");
		establecerAltoMando("Alto Mando.txt");
		establecerLideres("Gimnasios.txt");
		
		
		
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
				capturar();
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
	
	public static void capturar() throws FileNotFoundException {
		Scanner lector = new Scanner(new File("Habitats.txt"));
		Scanner sc = new Scanner(System.in);
		List habitats = new ArrayList<>();
		while (lector.hasNextLine()) {
			String linea = lector.nextLine();
			habitats.add(linea);
		}
		String opcion;
		do {
			System.out.println("¿Donde deseas ir a explorar?\n\nZonas disponibles:");
			int i = 0;
			for(i = 0; i < habitats.size(); i++) {
				System.out.println(i+1+") " + habitats.get(i));
			} System.out.println(i+1+") Volver al menu.");
			opcion = sc.nextLine();
			
			} while (Integer.valueOf(opcion) > 0 || Integer.valueOf(opcion) < habitats.size());
	}
	
	public static void zonas() {
		
	}
	
	public static void pc() {
		
	}
	
	public static void curar() {
		for (PokemonJugador p : jugador.getEquipo()) {
			if (p.getEstado().equals("Debilitado")) {
				p.setEstado("Vivo");
			}
		}
	}
	
	public static void guardar() throws IOException {
		try (BufferedWriter reescritor = new BufferedWriter(new FileWriter("Registros.txt"))) {
			reescritor.write(jugador.getNombre()+";"+jugador.getMedallas());
			reescritor.newLine();
			for (PokemonJugador p : jugador.getEquipo()) {
				reescritor.write(p.getPokemon().getNombre()+";"+p.getEstado());
				reescritor.newLine();
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

