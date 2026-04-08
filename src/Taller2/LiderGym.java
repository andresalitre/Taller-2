package Taller2;

import java.util.ArrayList;
import java.util.List;

public class LiderGym {
	private int orden;
	private String nombre;
	private String estado;
	private int cantPokemons;
	private List<Pokemon> equipo;
	
	public LiderGym(int orden, String nombre, String estado) {
		this.orden = orden;
		this.nombre = nombre;
		this.equipo = new ArrayList<>();
	}
	
	public void team() {
		for (Pokemon p : equipo) {
			System.out.println(p.getNombre());
		}
	}
	
	public void obtenerPokemon(Pokemon p) {
		equipo.add(p);
	}
	
	public String getNombre() {
		return nombre;
	}

}
