package tenis;

import java.awt.Color;

import j2d.JObjeto;
import j2d.JObjetoCirculo;

public class Pelota extends JObjetoCirculo {
	
	public Pelota(String nombre, int radio) {
		super(nombre, radio, Color.WHITE);
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
			escena.getJugadorDer().puntua();
			escena.reiniciaPelota();
			break;
		case "escena.paredDer":
			escena.getJugadorIzq().puntua();
			escena.reiniciaPelota();
			break;
		}
	}
}
