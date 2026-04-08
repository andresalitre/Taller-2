package Taller2;

import java.util.ArrayList;
import java.util.List;

public class AltoMando {
	private int orden;
	private String nombre;
	private List<Pokemon> equipo = new ArrayList<>();
	
	public AltoMando(int orden, String nombre, List<Pokemon> equipo) {
		this.orden = orden;
		this.nombre = nombre;
		this.equipo = equipo;
	}

}
