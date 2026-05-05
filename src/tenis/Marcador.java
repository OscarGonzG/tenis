package tenis;

import java.awt.Color;
import java.awt.Font;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import j2d.Juego;
import j2d.mods.JObjetoVisNumTexto;
import j2d.mods.multijugador.GestorMultijugadorCliente;
import j2d.mods.multijugador.VariableRed;

/**
 * Marcador que muestra una puntuacion.
 *
 * @author Óscar González García
 * @version jun-2026
 */
public class Marcador extends JObjetoVisNumTexto {

	private File ficheroLog;
	private FileWriter writer;
	private static final int TAMANHO_FUENTE = 32;

	private VariableRed<Integer> puntuacion;
	
	public Marcador(String nombre) {
		super(nombre, "", 0);
		puntuacion = Juego.nuevaVariableRed(Integer.class, nombre + ".puntuacion", 0);
		asignaFuente(Font.DIALOG, Font.BOLD, TAMANHO_FUENTE);
		asignaColor(Color.WHITE);
		
		String nombreLog;
		if (Juego.esCliente()) {
			nombreLog = "cliente" + ((GestorMultijugadorCliente)Juego.gestorMultijugador()).idJugador();
		} else {
			nombreLog = "servidor";
		}
		ficheroLog = new File(nombre + "-" + nombreLog + ".csv");
		try {
			writer = new FileWriter(ficheroLog);
			writer.write("valor,tiempoMs\n");
		} catch (IOException e) {
			System.out.println(-1);
		}
		puntuacion.anhadeSuscriptor(v -> {
			try {
				writer.write(v.valor() + "," + System.currentTimeMillis() + "\n");
			} catch (IOException e) {
				System.err.println("Recepcion de " + v.valor() + " no registrada");
			}
			asignaValor(v.valor());
		});
	}
	
	/**
	 * Suma un punto.
	 */
	public void puntua() {
		int nuevoValor = puntuacion.valor() + 1;
		try {
			writer.write(nuevoValor + "," + System.currentTimeMillis() + "\n");
		} catch (IOException e) {
			System.err.println("Envio de " + nuevoValor + " no registrado");
		}
		puntuacion.asignaValor(nuevoValor);
	}
}
