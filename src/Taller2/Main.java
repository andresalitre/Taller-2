package Taller2;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
		private static List<AltoMando> listaAltoMando = new ArrayList<>(); //Listas para objetos
		private static List<LiderGym> listaLiderGym = new ArrayList<>();
		private static List<Pokemon> Pokedex = new ArrayList<>();
		
	public static void main(String[] args) throws FileNotFoundException {
		establecerPokedex("Pokedex.txt");
		establecerAltoMando("Alto Mando.txt");
		establecerLideres("Gimnasios.txt");
		
		
		
	}
	
	public static void establecerAltoMando(String archivo) throws FileNotFoundException {
		Scanner lector = new Scanner(new File(archivo));
		while (lector.hasNextLine()) {
			 String linea = lector.nextLine();
			 String[] partes = linea.split(";");
			 AltoMando persona = new AltoMando(Integer.valueOf(partes[0]), partes[1]);
			 listaAltoMando.add(persona); // Por cada linea creamos un alto mando
			 for (int i = 2; i < 8; i++) { //Se revisa cada pokemon tipo (String de la linea)
				 for (Pokemon pokemon : Pokedex) { //Se revisa cada Nombre de los pokemon en la pokedex
					 if (partes[i].equals(pokemon.getNombre())) { //Se revisa si es igual el pokemon de la pokedex con el del alto mando
						 persona.obtenerPokemon(pokemon); //Si son iguales se le añade
					 }
				 }
			 }
		}
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
			 
		}
	}
	
	public static void establecerLideres(String archivo) throws FileNotFoundException {
		Scanner lector = new Scanner(new File(archivo));
		while (lector.hasNextLine()) {
			 String linea = lector.nextLine();
			 String[] partes = linea.split(";");
			 LiderGym persona = new LiderGym(Integer.valueOf(partes[0]), partes[1], partes[2]);
			 listaLiderGym.add(persona);
			 for (int i = 3; i < 4 + Integer.valueOf(partes[3]); i++) {
				 for (Pokemon pokemon : Pokedex) {
					 if (partes[i].equals(pokemon.getNombre())) {
						 persona.obtenerPokemon(pokemon);
					 }
				 }
			 }
		}
	}

}

// ("Magikarp","Noivern","Excadrill")
// ("Steelix","Lucario","Scizor")