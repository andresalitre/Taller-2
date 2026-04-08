package Taller2;

import java.util.ArrayList;
import java.util.List;

public class AltoMando {
	private int orden;
	private String nombre;
	private List<Pokemon> equipo;
	
	public AltoMando(int orden, String nombre) {
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
