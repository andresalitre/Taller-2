package Taller2;

public class Pokemon {
	// Magikarp;Lago;0.15;20;10;55;15;20;80;Agua
	
	private String nombre;
	private String habitad;
	private double aparicion;
	private int hp; //vida
	private int atk; //ataque
	private int spAtk; //ataque especial
	private int def; //defensa
	private int spDef; //defensa especial
	private int spd; //velocidad
	private String tipo; //monotype
	
	public Pokemon(String nombre,String habitad,double aparicion,int hp, 
			int atk, int spAtk, int def, int spDef, int spd,String tipo) {
		this.nombre = nombre;
		this.habitad = habitad;
		this.aparicion = aparicion;
		this.hp = hp;
		this.atk = atk;
		this.spAtk = spAtk;
		this.def = def;
		this.spDef = spDef;
		this.spd = spd;
		this.tipo = tipo;
	}
	
	

}
