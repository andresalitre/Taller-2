package Taller2;

public class PokeJugador {
	private Pokemon pokemon;
	private Boolean estado;
	
	public PokeJugador(Pokemon pokemon, Boolean estado) {
		this.pokemon = pokemon;
		this.estado = estado;
	}
	
	public String getEstado() {
		if (estado) return "Vivo";
		else return "Debilitado";
	}

}
