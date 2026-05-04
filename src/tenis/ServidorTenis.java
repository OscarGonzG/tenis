package tenis;

import java.io.IOException;
import java.net.InetSocketAddress;

import j2d.Juego;
import j2d.mods.multijugador.GestorMultijugadorServidor;

public class ServidorTenis {

	public static void main(String[] args) {
		GestorMultijugadorServidor servidor = null;
		Juego.asignaCiclosPorSegundo(120);
		try {
		servidor =
				new GestorMultijugadorServidor(2, new InetSocketAddress(6000));
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(-1);
		}
		Juego.asignaNombre("Servidor");
			Juego.asignaGestorMultijugador(servidor);

		EscenaTenis escena = new EscenaTenis();
		Juego.anhadeEscena(escena);
		Juego.jugar();
	}

}
