package Taller2;

import java.util.ArrayList;
import java.util.List;

public class Jugador {
	private String nombre;
	private String medallas;
	private List<PokemonJugador> equipo;
	
	public Jugador(String nombre, String medallas) {
		this.nombre = nombre;
		this.medallas = medallas;
		this.equipo = new ArrayList<>();
	}
	
	public void team() { //Printea el equipo del jugador, con lo que se pide, stats, nombre tipo, tambien agregue estado
		if (equipo.size() == 0) {System.out.println("\nNo tiene ningun pokemon"); return;}
		System.out.println();
		int j = 0;
		for (int i = 0; i < equipo.size(); i++) {
			if (j < 6) {
			Pokemon p = equipo.get(i).getPokemon();
			System.out.println("-"+p.getNombre()
			+ "|" + p.getTipo() + "|Stats: " + p.sumaStats() + "|" + equipo.get(i).getEstado());
			j++;
			}
		} System.out.println();
	}
	
	public void obtenerPokemon(Pokemon p,String estado) { //agrega un poke al jugador
		equipo.add(new PokemonJugador(p, estado));
	}
	
	public String getNombre() {
		return nombre;
	}
	
	public String getMedallas() {
		return medallas;
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public void setMedallas(String medallas) {
		this.medallas = medallas;
	}
	
	public List<PokemonJugador> getEquipo() {
		return equipo;
	}
	
	public void cambiarPokes(int entra,int sale) { 
		PokemonJugador aux = equipo.get(sale);
		equipo.set(sale, equipo.get(entra));
		equipo.set(entra, aux);
	} //Se crea un auxiliar para guardar el que sale, se cambian las posiciones y luego se agrega el auxiliar

}
