package Taller2;

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
	
	public void habitantes() {
		for (Pokemon p : habitantes) {
			System.out.println(p.getNombre());
		}
	}
}
