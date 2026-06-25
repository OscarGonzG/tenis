package tenis;

import java.awt.Color;
import java.awt.Font;

import j2d.Juego;
import j2d.mods.JObjetoVisNumTexto;
import j2d.mods.multijugador.VariableRed;

/**
 * Marcador que muestra una puntuacion.
 *
 * @author Óscar González García
 * @version jun-2026
 */
public class Marcador extends JObjetoVisNumTexto {
	
	private static final int TAMANHO_FUENTE = 32;

	private VariableRed<Integer> puntuacion;
	
	public Marcador(String nombre) {
		super(nombre, "", 0);
		puntuacion = Juego.nuevaVariableRed(Integer.class, nombre + ".puntuacion", 0);
		asignaFuente(Font.DIALOG, Font.BOLD, TAMANHO_FUENTE);
		asignaColor(Color.WHITE);
		
		puntuacion.anhadeSuscriptor(v -> asignaValor(v.valor()));
	}
	
	/**
	 * Suma un punto.
	 */
	public void puntua() {
		puntuacion.asignaValor(puntuacion.valor() + 1);
	}
}
