package tenis;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.UnknownHostException;

import javax.swing.JOptionPane;

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
		
		SocketAddress dirServidor = null;
		while (dirServidor == null) {
			String stringDirServidor = JOptionPane.showInputDialog(null,
					"Introduce HOST:PUERTO del servidor",
					"Indica el servidor", JOptionPane.PLAIN_MESSAGE);
			
			if (stringDirServidor == null) {
				System.out.println("a");
				System.exit(-1);
			}
			
			String[] strings = stringDirServidor.split(":");
			
			if (strings.length != 2) {
				System.out.println("b");
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
		
		System.out.println(dirServidor);
		
		Juego.asignaNombre("Tenis");
		Juego.asignaCiclosPorSegundo(120);
		try {
			Juego.asignaGestorMultijugador(new GestorMultijugadorCliente(null, dirServidor));
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(-1);
		}
		
		EscenaTenis escena = new EscenaTenis();
		Juego.anhadeEscena(escena);
		Juego.jugar();
	}
}
