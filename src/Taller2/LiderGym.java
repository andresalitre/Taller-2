package Taller2;

import java.util.ArrayList;
import java.util.List;

public class LiderGym {
	private int orden;
	private String nombre;
	private String estado;
	private List<Pokemon> equipo;
	
	public LiderGym(int orden, String nombre, String estado) {
		this.orden = orden;
		this.nombre = nombre;
		this.estado = estado;
		this.equipo = new ArrayList<>();
	}
	
	public void team() {
		for (Pokemon p : equipo) {
			System.out.println("-"+p.getNombre());
		}
	}
	
	public void obtenerPokemon(Pokemon p) {
		equipo.add(p);
	}
	
	public List<Pokemon> setearTeam() {
		List<Pokemon> equipoPelea = new ArrayList<>();
		int j = 0;
		for (int i = 0; j < equipo.size(); i++) {
			if (j < 6) {
				Pokemon p = equipo.get(i);
				equipoPelea.add(p);
				j++;
			}
		} return equipoPelea;
	}
	
	public String getNombre() {
		return nombre;
	}
	
	public String getEstado() {
		return estado;
	}
	
	public int getOrden() {
		return orden;
	}

}
