package tenis;

import j2d.mods.IVisualizadorNumerico;

public class Jugador {
	private int puntuacion = 0;
	private final IVisualizadorNumerico contador;
	
	public Jugador(IVisualizadorNumerico contador) {
		this.contador = contador;
	}
	
	public void puntua() {
		puntuacion++;
		contador.asignaValor(puntuacion);
	}
}
