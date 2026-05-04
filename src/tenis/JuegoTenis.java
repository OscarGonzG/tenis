package tenis;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;

import j2d.Juego;
import j2d.mods.multijugador.GestorMultijugadorCliente;

/**
 * Juego de tenis.
 *  
 * @author Óscar González García
 * @version mar-2026
 */
public class JuegoTenis {
	public static void main(String[] args) {
		Juego.asignaNombre("Tenis");
		Juego.asignaCiclosPorSegundo(120);
		try {
			Juego.asignaGestorMultijugador(new GestorMultijugadorCliente(null, new InetSocketAddress(InetAddress.getLocalHost(), 6000)));
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(-1);
		}
		
		EscenaTenis escena = new EscenaTenis();
		Juego.anhadeEscena(escena);
		Juego.jugar();
	}
}
