package tenis;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.UnknownHostException;

import javax.swing.JOptionPane;

import j2d.Juego;
import j2d.mods.multijugador.GestorMultijugadorServidor;

public class ServidorTenis {

	public static void main(String[] args) {

		SocketAddress dirServidor = null;
		while (dirServidor == null) {
			String stringDirServidor = JOptionPane.showInputDialog(null,
					"Introduce HOST:PUERTO del servidor",
					"Indica el servidor", JOptionPane.PLAIN_MESSAGE);
			
			if (stringDirServidor == null) {
				System.exit(-1);
			}
			
			String[] strings = stringDirServidor.split(":");
			
			if (strings.length != 2) {
				System.exit(-1);				
			}
			
			try {
				int puerto = Integer.parseInt(strings[1]);
				InetAddress ipServidor = InetAddress.getByName(strings[0]);
				dirServidor = new InetSocketAddress(ipServidor, puerto);
			} catch (UnknownHostException e) {
				JOptionPane.showMessageDialog(null, "Host \"" + strings[0] + 
						"\" desconocido", "ERROR", JOptionPane.ERROR_MESSAGE);
			} catch (IllegalArgumentException e ) {
				JOptionPane.showMessageDialog(null, "Puerto \"" + strings[1] +
						"\" no válido", "ERROR", JOptionPane.ERROR_MESSAGE);
			}
		}
		
		GestorMultijugadorServidor servidor = null;
		try {
		servidor =
				new GestorMultijugadorServidor(2, dirServidor);
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
