package tenis;

import java.awt.Color;
import java.awt.Point;

import j2d.JObjeto;
import j2d.JObjetoCirculo;
import j2d.Juego;
import j2d.mods.multijugador.VariableRed;

/**
 * Pelota en el juego de tenis.
 *  
 * @author Óscar González García
 * @version abr-2026
 */
public class Pelota extends JObjetoCirculo {
	
	private final VariableRed<Point> pos;
	
	public Pelota(String nombre, int radio) {
		super(nombre, radio, Color.WHITE);
		pos = Juego.nuevaVariableRed(Point.class, nombre + ".pos", new Point());
		notificacionSalidaPantallaActiva(); // evita que se destruya
		if (Juego.esCliente()) {
			colisionador().desactiva();
			pos.anhadeSuscriptor(v -> {
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
			pos.asignaValor(posicion());
		}
	}
}
