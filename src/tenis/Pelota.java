package tenis;

import java.awt.Color;
import java.awt.Point;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import j2d.JObjeto;
import j2d.JObjetoCirculo;
import j2d.Juego;
import j2d.mods.multijugador.GestorMultijugadorCliente;
import j2d.mods.multijugador.VariableRed;

/**
 * Pelota en el juego de tenis.
 *  
 * @author Óscar González García
 * @version abr-2026
 */
public class Pelota extends JObjetoCirculo {

	private File ficheroLog;
	private FileWriter writer;
	private final VariableRed<PointDepuracion> pos;
	
	public Pelota(String nombre, int radio) {
		super(nombre, radio, Color.WHITE);
		pos = Juego.nuevaVariableRed(PointDepuracion.class, nombre + ".posX", new PointDepuracion(0, 0, 0));
		
		String nombreLog;
		if (Juego.esCliente()) {
			nombreLog = "cliente" + ((GestorMultijugadorCliente)Juego.gestorMultijugador()).idJugador();
		} else {
			nombreLog = "servidor";
		}
		ficheroLog = new File(nombre + "-" + nombreLog + ".csv");
		try {
			writer = new FileWriter(ficheroLog);
			writer.write("numSecuencia,tiempoMs\n");
		} catch (IOException e) {
			System.out.println(-1);
		}
		
		if (Juego.esCliente()) {
			colisionador().desactiva();
			pos.anhadeSuscriptor(v -> {
				try {
					writer.write(v.valor().numSecuencia + "," + System.currentTimeMillis() + "\n");
				} catch (IOException e) {
					System.err.println("Recepcion de " + v.valor() + " no registrada");
				}
				this.posiciona(v.valor());
			});
		}
	}

	
	@Override
	public void colision(JObjeto otroObj, boolean causante) {
		EscenaTenis escena = (EscenaTenis) escena();
		switch (otroObj.nombre()) {
		case "escena.suelo":
		case "escena.techo":
			asignaVelY(-velY());
			break;
		case "escena.paredIzq":
			escena.getMarcadorDer().puntua();
			escena.reiniciaPelota();
			break;
		case "escena.paredDer":
			escena.getMarcadorIzq().puntua();
			escena.reiniciaPelota();
			break;
		}
	}
	
	@Override
	public void ciclo() {
		if (Juego.esServidor()) {
			int nuevoNumSecuencia = pos.valor().numSecuencia + 1;
			try {
				writer.write(nuevoNumSecuencia + "," + System.currentTimeMillis() + "\n");
			} catch (IOException e) {
				System.err.println("Envio de valor " + nuevoNumSecuencia + " no registrado");
			}
			Point posActual = posicion();
			pos.asignaValor(new PointDepuracion(posActual.x, posActual.y, nuevoNumSecuencia));
		}
	}
}
