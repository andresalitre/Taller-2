package Taller2;

import java.util.Random;

import java.util.ArrayList;
import java.util.List;

public class Habitat {
	String nombre;
	private List<Pokemon> habitantes;
	
	public Habitat(String nombre) {
		this.nombre = nombre;
		this.habitantes = new ArrayList<>();
		
	}

	public String getNombre() {
		return nombre;
	}
	
	public void agregarPokemon(Pokemon p) {
		habitantes.add(p);
	}
	
	public List<Pokemon> getHabitantes() {
		return habitantes;
	}
	
	public Pokemon obtenerRandom() {
		List<Pokemon> lista = new ArrayList<>();
		for (Pokemon p : habitantes) {
			int i = (int) (p.getAparicion()*100);
			for (int j = 0; j < i; j++) {
				lista.add(p);
			}
		}	
		Random rand = new Random();
		Pokemon p = lista.get(rand.nextInt(lista.size()));
		return p;
	}
}
