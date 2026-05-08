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
	private static List<AltoMando> listaAltoMando = new ArrayList<>(); // Listas para objetos
	private static List<LiderGym> listaLiderGym = new ArrayList<>();
	private static List<Pokemon> Pokedex = new ArrayList<>();
	private static List<Habitat> Habitats = new ArrayList<>();
	private static Jugador jugador = new Jugador("", "");
	static Boolean ejecutar = true;
	static Boolean cargaCompleta = false;

	public static void main(String[] args) throws IOException {
		establecerPokedex("Pokedex.txt");
		establecerAltoMando("Alto Mando.txt");
		establecerLideres("Gimnasios.txt");
		establecerZonas("Habitats.txt");

		menuInicial();
	}

	public static void menuInicial() throws IOException {
		Scanner sc = new Scanner(System.in);
		String opcion;
		do {
			System.out.print("1) Continuar.\n" + "2) Nueva Partida.\n" + "3) Salir.\nIngresar opción: ");
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
			case "3":
				ejecutar = false;
				System.out.println("Nos vemos entrenador...");
				break;
			default:
				System.out.println("\nOpción invalida.\n");
				break;
			}

		} while (ejecutar);
		sc.close();

	} // menu donde se carga partida, se crea una nueva o te vas

	public static void menuPartida() throws IOException {
		System.out.print("\nBienvenido " + jugador.getNombre());
		Scanner sc = new Scanner(System.in);
		String opcion;
		do {
			System.out.print("\n" + jugador.getNombre() + ", que deseas hacer?" + "\n\n" + "1) Revisar equipo.\r\n"
					+ "2) Salir a capturar.\r\n" + "3) Acceso al PC (cambiar Pokemon del equipo).\r\n"
					+ "4) Retar un gimnasio.\r\n" + "5) Desafío al Alto Mando.\r\n" + "6) Curar Pokemon.\r\n"
					+ "7) Guardar.\r\n" + "8) Guardar y Salir.\n\nSeleccionar opción: ");
			opcion = sc.nextLine();

			switch (opcion) {
			case "1":
				jugador.team();
				break;
			case "2":
				elegirZona();
				break;
			case "3":
				pc();
				break;
			case "4":
				printGyms();
				break;
			case "5":
				batallaAltoMando();
				break;
			case "6":
				jugador.curarEquipo(jugador.getEquipo());
				break;
			case "7":
				guardar();
				System.out.println("La partida ha sido guardada");
				break;
			case "8":
				ejecutar = false;
				guardar();
				System.out.println("Nos vemos entrenador...");
				return;
			default:
				System.out.println("\nOpción invalida.\n");
				break;
			}

		} while (!opcion.equals("8"));
	}

	public static void batallaLider(LiderGym rival) {
		List<Pokemon> equipoRival = rival.setearTeam();
		int indiceRival = 0;
		int pokemonActual = primerVivo();

		if (pokemonActual == -1) {
			System.out.println("No tienes pokemon disponibles para pelear.");
			return;
		}

		System.out.println("\nDesafiando a " + rival.getNombre() + "!!");
		System.out.println(rival.getNombre() + " saca a " + equipoRival.get(indiceRival).getNombre() + "!");
		System.out.println(jugador.getNombre() + " saca a "
				+ jugador.getEquipo().get(pokemonActual).getPokemon().getNombre() + "!");

		Scanner sc = new Scanner(System.in);
		String opcion;
		boolean batallaActiva = true;

		do {
			System.out.print("\n¿Qué deseas hacer?\n1) Atacar\n2) Cambiar de pokemon\n3) Rendirse\nIngrese Opción: ");
			opcion = sc.nextLine();

			switch (opcion) {
			case "1":
				boolean ganoJugador = pelea(pokemonActual, equipoRival, indiceRival);
				if (ganoJugador) {
					indiceRival++;
					if (indiceRival >= equipoRival.size()) {
						System.out.println("\n¡Derrotaste a " + rival.getNombre() + "!");
						rival.setEstado("Derrotado");
						jugador.setMedallas(jugador.getMedallas().equals("none") ? rival.getNombre()
								: jugador.getMedallas() + ";" + rival.getNombre());
						batallaActiva = false;
					} else {
						System.out.println(
								rival.getNombre() + " saca a " + equipoRival.get(indiceRival).getNombre() + "!");
					}
				} else {
					pokemonActual = primerVivo();
					if (pokemonActual == -1) {
						System.out.println("\n¡Te quedaste sin pokemon! Volviendo al menú...");
						batallaActiva = false;
					} else {
						System.out.println(
								"Sacas a " + jugador.getEquipo().get(pokemonActual).getPokemon().getNombre() + "!");
					}
				}
				break;
			case "2":
				pokemonActual = cambiarEnPelea(pokemonActual);
				break;
			case "3":
				System.out.println("Te has rendido. El gimnasio sigue Sin derrotar.");
				batallaActiva = false;
				break;
			default:
				System.out.println("Opción inválida.");
			}
		} while (batallaActiva);
	}

	public static void batallaAltoMando() {
		int gimnasiosDerrotados = 0;
		for (LiderGym l : listaLiderGym) {
			if (l.getEstado().equals("Derrotado")) {
				gimnasiosDerrotados++;
			}
		}
		if (gimnasiosDerrotados < 8) {
			System.out.println(
					"\nDebes derrotar los 8 gimnasios primero. Tienes " + gimnasiosDerrotados + " medalla(s).");
			return;
		}
		if (primerVivo() == -1) {
			System.out.println("\nNo tienes pokemon disponibles. ¡Cura tu equipo primero!");
			return;
		}

		System.out.println("\n¡Bienvenido al Alto Mando, " + jugador.getNombre() + "!");
		System.out.println("Deberás enfrentar a todos de forma consecutiva.");
		System.out.println("No podrás acceder al PC ni curar entre batallas.\n");

		for (int i = 0; i < listaAltoMando.size(); i++) {
			AltoMando miembro = listaAltoMando.get(i);
			System.out.println("\n--- Miembro " + (i + 1) + ": " + miembro.getNombre() + " ---");
			boolean resultado = batallaAltoMandoMiembro(miembro);
			if (!resultado) {
				System.out.println("\nFuiste derrotado. ¡Vuelve más fuerte, " + jugador.getNombre() + "!");
				return;
			}
		}

		System.out.println("\n¡¡FELICITACIONES " + jugador.getNombre().toUpperCase() + "!!");
		System.out.println("¡Has derrotado al Alto Mando y te has coronado CAMPEÓN!");
	}

	public static boolean batallaAltoMandoMiembro(AltoMando rival) {
		List<Pokemon> equipoRival = rival.getEquipo();
		int indiceRival = 0;
		int pokemonActual = primerVivo();

		if (pokemonActual == -1) {
			System.out.println("No tienes pokemon disponibles.");
			return false;
		}

		System.out.println("\n" + rival.getNombre() + " saca a " + equipoRival.get(indiceRival).getNombre() + "!");
		System.out.println(jugador.getNombre() + " saca a "
				+ jugador.getEquipo().get(pokemonActual).getPokemon().getNombre() + "!");

		Scanner sc = new Scanner(System.in);
		String opcion;

		while (true) {
			System.out.print("\n¿Qué deseas hacer?\n1) Atacar\n2) Cambiar de pokemon\n3) Rendirse\nIngrese Opción: ");
			opcion = sc.nextLine();

			switch (opcion) {
			case "1":
				boolean ganoJugador = pelea(pokemonActual, equipoRival, indiceRival);
				if (ganoJugador) {
					indiceRival++;
					if (indiceRival >= equipoRival.size()) {
						System.out.println("\n¡Derrotaste a " + rival.getNombre() + "!");
						return true;
					}
					System.out.println(rival.getNombre() + " saca a " + equipoRival.get(indiceRival).getNombre() + "!");
				} else {
					pokemonActual = primerVivo();
					if (pokemonActual == -1) {
						System.out.println("\n¡Te quedaste sin pokemon!");
						return false;
					}
					System.out.println(
							"Sacas a " + jugador.getEquipo().get(pokemonActual).getPokemon().getNombre() + "!");
				}
				break;
			case "2":
				pokemonActual = cambiarEnPelea(pokemonActual);
				break;
			case "3":
				System.out.println("\nTe rendiste.");
				return false;
			default:
				System.out.println("Opción inválida.");
			}
		}
	}

	public static int cambiarEnPelea(int pokemonActual) {
		List<PokemonJugador> equipo = jugador.getEquipo();
		boolean hayVivos = false;
		int limite = equipo.size() < 6 ? equipo.size() : 6;

		System.out.println("\nTu equipo:");
		for (int i = 0; i < limite; i++) {
			String estado = equipo.get(i).getEstado();
			System.out.println((i + 1) + ") " + equipo.get(i).getPokemon().getNombre() + " [" + estado + "]");
			if (estado.equals("Vivo") && i != pokemonActual) {
				hayVivos = true;
			}
		}

		if (!hayVivos) {
			System.out.println("No tienes otros pokemon disponibles.");
			return pokemonActual;
		}

		System.out.print("Escoge el pokemon para intercambiar: ");
		int eleccion = validarRango(1, limite) - 1;

		if (eleccion == pokemonActual) {
			System.out.println("Ya tienes ese pokemon en batalla.");
			return pokemonActual;
		}
		if (equipo.get(eleccion).getEstado().equals("Debilitado")) {
			System.out.println("Ese pokemon está debilitado, elige otro.");
			return pokemonActual;
		}

		System.out.println("Sale " + equipo.get(pokemonActual).getPokemon().getNombre() + " y entra "
				+ equipo.get(eleccion).getPokemon().getNombre() + "!");
		return eleccion;
	}

	public static int primerVivo() {
		List<PokemonJugador> equipo = jugador.getEquipo();
		int limite = equipo.size() < 6 ? equipo.size() : 6;
		for (int i = 0; i < limite; i++) {
			if (equipo.get(i).getEstado().equals("Vivo")) {
				return i;
			}
		}
		return -1;
	}

	public static boolean pelea(int pokemonActual, List<Pokemon> equipoRival, int indiceRival) {
		TablaTipos tabla = new TablaTipos(); // crea la tabla tipos

		Pokemon PokemonJugador = jugador.getEquipo().get(pokemonActual).getPokemon(); // pokemon del momento de cada
																						// jugador
		Pokemon PokemonRival = equipoRival.get(indiceRival);

		int statsJugador = PokemonJugador.sumaStats(); // stats para la pelea
		int statsRival = PokemonRival.sumaStats();
		String[] tipos = { PokemonJugador.getTipo(), PokemonRival.getTipo() };
		double multiplicador = tabla.MultiplicadorDeAtaque(tipos); // calcula el daño hecho según la matriz de tabla de
																	// tipos usando como índice los tipos
		if (multiplicador < 0) {
			System.out.println("(Tipo desconocido, se aplica multiplicador neutro)");
			multiplicador = 1.0;
		}

		System.out.println("\n" + PokemonJugador.getNombre() + " -> " + statsJugador + " puntos");
		System.out.println(PokemonRival.getNombre() + " -> " + statsRival + " puntos");

		if (multiplicador == 2.0) {
			System.out.println("\n¡" + PokemonJugador.getNombre() + " es súper efectivo!");
			statsJugador *= 2;
		} else if (multiplicador == 0.5) {
			System.out.println("\n" + PokemonJugador.getNombre() + " no es muy efectivo...");
			statsJugador /= 2;
		} else if (multiplicador == 0.0) {
			System.out.println("\n¡No afecta a " + PokemonRival.getNombre() + "!");
			statsJugador = 0;
		}

		System.out.println("\nPuntaje final:");
		System.out.println(PokemonJugador.getNombre() + " -> " + statsJugador + " puntos");
		System.out.println(PokemonRival.getNombre() + " -> " + statsRival + " puntos");

		if (statsJugador > statsRival) {
			System.out.println("\n¡Ha ganado " + PokemonJugador.getNombre() + "!");
			return true;
		} else {
			System.out.println("\nHa ganado " + PokemonRival.getNombre() + "! " + PokemonJugador.getNombre()
					+ " ha sido derrotado...");
			jugador.getEquipo().get(pokemonActual).setEstado("Debilitado");
			return false;
		}
	}

	public static void elegirGym(int opcion) {
		if (opcion == 0) {
			// System.out.println("Desafía a EmmaLaArdillaRabiosa!!!11!\n");
			batallaLider(listaLiderGym.get(opcion));
			return;
		}
		if (listaLiderGym.get(opcion - 1).getEstado().equals("Sin derrotar")) {
			System.out.println("Primero debes derrotar a " + listaLiderGym.get(opcion - 1).getNombre()
					+ "\npara enfrentarte a " + listaLiderGym.get(opcion).getNombre());
		} else {
			System.out.println("Ponte vio contra " + listaLiderGym.get(opcion).getNombre() + "\n");
			batallaLider(listaLiderGym.get(opcion));
		}

	}

	public static void printGyms() {

		Scanner sc = new Scanner(System.in);
		String opcion = "";
		do {
			System.out.println("\nA cual Lider deseas retar??\r\n");
			for (LiderGym l : listaLiderGym) {
				System.out.println(l.getOrden() + ") " + l.getNombre() + " - " + "Estado: " + l.getEstado());
			}
			System.out.println("9) Volver al menu.\n");
			System.out.print("Seleccionar opción: ");
			opcion = sc.nextLine();
			switch (opcion) {
			case "1":
				elegirGym(0);
				break;
			case "2":
				elegirGym(1);
				break;
			case "3":
				elegirGym(2);
				break;
			case "4":
				elegirGym(3);
				break;
			case "5":
				elegirGym(4);
				break;
			case "6":
				elegirGym(5);
				break;
			case "7":
				elegirGym(6);
				break;
			case "8":
				elegirGym(7);
				break;
			case "9":
				System.out.println("Volviendo...");
				break;
			default:
				System.out.println("no válida");
				break;
			}
		} while (!opcion.equals("9"));

		// } while (!opcion.equals("9"));

	} // printea los lideres en orden con su estado

	public static void establecerAltoMando(String archivo) throws FileNotFoundException {
		Scanner lector = new Scanner(new File(archivo));
		while (lector.hasNextLine()) {
			String linea = lector.nextLine();
			String[] partes = linea.split(";");
			AltoMando persona = new AltoMando(Integer.valueOf(partes[0]), partes[1]);
			listaAltoMando.add(persona); // Por cada linea creamos un alto mando
			for (int i = 2; i < 8; i++) { // Para revisar cada pokemon tipo (String de la linea)
				for (Pokemon pokemon : Pokedex) { // Se revisa cada Nombre de los pokemon en la pokedex
					if (partes[i].equals(pokemon.getNombre())) { // Se revisa si es igual el pokemon de la pokedex con
																	// el del alto mando
						persona.obtenerPokemon(pokemon); // Si son iguales se le añade
					}
				}
			}
		}
	} // se crean todos los altosMandos tipo objetos y se guardan una lista

	public static void establecerPokedex(String archivo) throws FileNotFoundException {
		Scanner lector = new Scanner(new File(archivo));
		while (lector.hasNextLine()) {
			String linea = lector.nextLine();
			String[] partes = linea.split(";");
			Pokemon poke = new Pokemon(partes[0], partes[1], Double.valueOf(partes[2]), Integer.valueOf(partes[3]),
					Integer.valueOf(partes[4]), Integer.valueOf(partes[5]), Integer.valueOf(partes[6]),
					Integer.valueOf(partes[7]), Integer.valueOf(partes[8]), partes[9]);
			Pokedex.add(poke);
			// Se añaden todos los pokemon del archivo a la pokedex
		}
	} // se crean los pokemon y luego se guardan en una lista

	public static void establecerLideres(String archivo) throws FileNotFoundException {
		Scanner lector = new Scanner(new File(archivo));
		while (lector.hasNextLine()) {
			String linea = lector.nextLine();
			String[] partes = linea.split(";");
			LiderGym persona = new LiderGym(Integer.valueOf(partes[0]), partes[1], partes[2]);
			listaLiderGym.add(persona); // Se crea un lider por cada linea
			for (int i = 3; i < 4 + Integer.valueOf(partes[3]); i++) {
				// dependiendo de la cantidad de pokes del lider, se revisa los pokemon tipo
				// String de la linea
				for (Pokemon pokemon : Pokedex) { // Se revisa toda la pokedex
					if (partes[i].equals(pokemon.getNombre())) { // Se revisa que coincidan los pokes
						persona.obtenerPokemon(pokemon); // Se añade el pokemon al Lider si coinciden
					}
				}
			}
		}
	} // se crean todos los lideres tipo objetos y se guardan una lista

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
	} // se crean todos los habitats con sus pokemon correspondientes en una lista

	public static void pc() {
		Scanner sc = new Scanner(System.in);
		String opcion;

		do {
			// Siempre muestra equipo y PC actualizados
			System.out.println("\nEquipo (primeros 6):");
			int limite = jugador.getEquipo().size() < 6 ? jugador.getEquipo().size() : 6;
			for (int i = 0; i < limite; i++) {
				System.out.println((i + 1) + ") " + jugador.getEquipo().get(i).getPokemon().getNombre());
			}

			if (jugador.getEquipo().size() > 6) {
				System.out.println("\nPC:");
				for (int i = 6; i < jugador.getEquipo().size(); i++) {
					System.out.println((i + 1) + ") " + jugador.getEquipo().get(i).getPokemon().getNombre());
				}
			} else {
				System.out.println("\nNo tienes pokémon en el PC.");
			}

			System.out.print("\n1) Cambiar pokémon.\n2) Salir.\n\nOpción: ");
			opcion = sc.nextLine();

			switch (opcion) {
			case "1":
				if (jugador.getEquipo().size() <= 6) {
					System.out.println("\nNo tienes pokémon en el PC para intercambiar.");
					opcion = "2";
					break;
				}
				cambiarPokemon();
				break;
			case "2":
				System.out.println("\nVolviendo al menu...\n");
				break;
			default:
				System.out.println("\nOpción invalida.");
				break;
			}
		} while (!opcion.equals("2"));
	} // se muestran todos los pokemon del jugador y se le pregunta que si va a
		// cambiar el equipo

	public static void cambiarPokemon() {
		System.out.print("Elige quien va a salir del equipo: ");
		int sacar = validarRango(1, 6) - 1;
		System.out.print("Elige quien va a entrar al equipo: ");
		int meter = validarRango(7, jugador.getEquipo().size()) - 1;
		System.out.println(jugador.getEquipo().get(sacar).getPokemon().getNombre() + " salio del equipo por "
				+ jugador.getEquipo().get(meter).getPokemon().getNombre());
		jugador.cambiarPokes(meter, sacar);
	} // se usa validar rango para elegir un poke del equipo y uno del pc, luego se
		// llama a cambiarPoke en Jugador para intercambialos

	public static int validarRango(int minimo, int Maximo) {
		Scanner sc = new Scanner(System.in);
		int numero = -1;
		while (true) {
			if (sc.hasNextInt()) {
				numero = sc.nextInt();
				if (numero >= minimo && numero <= Maximo) {
					return numero;
				} else {
					System.out.print("Pokemon fuera de rango: ");
				}
			} else {
				System.out.print("Debes ingresar un número: ");
				sc.next();
			}
		}
	} // validar que se elija bien el numero entre el rango que se le da a la funcion

	public static void elegirZona() {
		Scanner sc = new Scanner(System.in);
		String opcion;
		do {
			System.out.print("\nDonde deseas ir a explorar?\r\n" + "\r\n" + "Zonas disponibles:\r\n" + "\r\n"
					+ "1) Lago\r\n" + "2) Cueva\r\n" + "3) Montaña\r\n" + "4) Bosque\r\n" + "5) Prado\r\n"
					+ "6) Mar\r\n" + "7) Volver al menu.\n\nElección: ");
			opcion = sc.nextLine();
			switch (opcion) {
			case "1":
				capturar(Habitats.get(0));
				return;
			case "2":
				capturar(Habitats.get(1));
				return;
			case "3":
				capturar(Habitats.get(2));
				return;
			case "4":
				capturar(Habitats.get(3));
				return;
			case "5":
				capturar(Habitats.get(4));
				return;
			case "6":
				capturar(Habitats.get(5));
				return;
			case "7":
				break;
			default:
				System.out.println("Elección incorrecta, vuelve a intentarlo.");
			}

		} while (!opcion.equals("7"));
	} // un switch para elegir la zona, y segun la que se elegi, se llama a capturar()

	public static void capturar(Habitat habitat) {
		Pokemon p = habitat.obtenerRandom();
		Scanner sc = new Scanner(System.in);
		System.out.println("\nOh!! Ha aparecido un increible " + p.getNombre() + "\n\nQue deseas hacer?");
		String opcion;
		do {
			System.out.print("\n1) Capturar\n2) Huir\n\nOpción: ");
			opcion = sc.nextLine();
			switch (opcion) {
			case "1":
				for (PokemonJugador q : jugador.getEquipo()) {
					if (p.equals(q.getPokemon())) {
						System.out.println("\nYa tienes este pokemon.");
						return;
					}
				}
				jugador.obtenerPokemon(p, "Vivo");
				System.out.println("\n" + p.getNombre() + " capturado con exito!!\n");
				System.out.println(p.getNombre() + " ha sido agregado a tu equipo!");
				break;
			case "2":
				System.out.println("\nYou gotta away safely.");
				break;
			}

		} while (!opcion.equals("1") && !opcion.equals("2"));

	} // se elegi un pokemon random y pregunta si lo que quieres capturar o no, se
		// añade a la lista de pokemon del jugador, no se pueden capturar repetidos


	public static void guardar() throws IOException {
		try (BufferedWriter reescritor = new BufferedWriter(new FileWriter("Registros.txt"))) {
			reescritor.write(jugador.getNombre() + ";" + jugador.getMedallas());
			reescritor.newLine(); // escribir el nombre;medellas
			for (PokemonJugador p : jugador.getEquipo()) {
				reescritor.write(p.getPokemon().getNombre() + ";" + p.getEstado());
				reescritor.newLine(); // se recorren todo el equipo y se escribe pokemon;estado
			}
			for (LiderGym l : listaLiderGym) {
				reescritor.write("GYM;" + l.getNombre() + ";" + l.getEstado());
				reescritor.newLine(); // lo mismo pero con los gimnasios
			}
		}
	} // reescribe el archivo con el nombre, medallas y pokemon del jugador, junto al
		// estado

	public static void partidaNueva() throws IOException {
		Scanner sc = new Scanner(System.in);
		System.out.print("\nIngrese su apodo de jugador: ");
		String apodo = sc.nextLine();
		try (BufferedWriter reescritor = new BufferedWriter(new FileWriter("Registros.txt"))) {
			reescritor.write(apodo + ";" + "none");
			jugador.setNombre(apodo);
			jugador.setMedallas("none");
		}
	} // se pregunta nuevo apodo y se reescribe el archivo, queda: ("apodo";"none")

	public static void cargarPartida() throws IOException {
		Scanner lector = new Scanner(new File("Registros.txt"));
		int primeraLinea = 0;
		while (lector.hasNextLine()) {
			String linea = lector.nextLine();
			String[] partes = linea.split(";");

			if (primeraLinea == 0) {
				jugador.setNombre(partes[0]);
				// reconstruir medallas desde partes[1] en adelante
				if (partes.length == 2) {
					jugador.setMedallas(partes[1]);
				} else {
					String medallas = partes[1];
					for (int i = 2; i < partes.length; i++) {
						medallas += ";" + partes[i];
					}
					jugador.setMedallas(medallas);
				}
				primeraLinea = 1;
				continue;
			}

			// línea de gimnasio
			if (partes[0].equals("GYM")) {
				for (LiderGym l : listaLiderGym) {
					if (l.getNombre().equals(partes[1])) {
						l.setEstado(partes[2]);
					}
				}
				continue;
			}

			// línea de pokemon
			for (Pokemon pokemon : Pokedex) {
				if (partes[0].equals(pokemon.getNombre())) {
					jugador.obtenerPokemon(pokemon, partes[1]);
				}
			}
		}
		lector.close();
	} // se cargan los datos de registros.txt, se sobresciben los datos del objeto
		// "jugador"

}
