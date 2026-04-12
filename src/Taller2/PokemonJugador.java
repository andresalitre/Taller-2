package Taller2;

public class PokemonJugador {
	private Pokemon pokemon;
	private String estado;
	
	public PokemonJugador(Pokemon pokemon, String estado) {
		this.pokemon = pokemon;
		this.estado = estado;
	}
	
	public String getEstado() {
		return estado;
	}
	
	public void setEstado(String estado) {
		this.estado = estado;
	}
	
	public Pokemon getPokemon() {
		return pokemon;
	}
	
	public void curar() {
		this.estado = "Vivo";
	}
}
