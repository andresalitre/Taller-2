package Taller2;

import java.util.ArrayList;
import java.util.List;

public class Jugador {
	private String nombre;
	private int medallas;
	private List<Pokemon> equipo;
	
	public Jugador(String nombre, int medallas) {
		this.nombre = nombre;
		this.medallas = medallas;
		this.equipo = new ArrayList<>();
	}
	
	public void team() {
		if (equipo.size() == 0) {System.out.println("No tiene ningun pokemon"); return;}
		for (Pokemon p : equipo) {
			System.out.println("-"+p.getNombre());
		}
	}
	
	public void obtenerPokemon(Pokemon p) {
		equipo.add(p);
	}
	
	public String getNombre() {
		return nombre;
	}
	
	public int getMedallas() {
		return medallas;
	}

}
