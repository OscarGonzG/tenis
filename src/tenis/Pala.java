package tenis;

import java.awt.Color;

import j2d.IControladoTeclado;
import j2d.JObjeto;
import j2d.JObjetoRectangulo;

public class Pala extends JObjetoRectangulo implements IControladoTeclado {
	
	private static final int ALTURA_BASE = 75;
	private static final int ANCHURA_BASE = 5;
	public static final int VELOCIDAD_BASE = 8;
	
	private static final float MULTIPLICADOR_VELOCIDAD_BORDES = 0.1f;
	
	private final Controles controles;

	/**
	 * Construye una pala.
	 * @param nombre nombre del JObjeto.
	 * @param controles controles con los que mover la pala.
	 */
	public Pala(String nombre, Controles controles) {
		super(nombre, ANCHURA_BASE, ALTURA_BASE, Color.WHITE);
		this.controles = controles;
	}

	@Override
	public void teclaPresionada(int codigoTecla) {
		if (codigoTecla == controles.teclaArriba()) {
			this.asignaVelY(-VELOCIDAD_BASE);
		} else if (codigoTecla == controles.teclaAbajo()) {
			this.asignaVelY(VELOCIDAD_BASE);
		}
	}

	@Override
	public void teclaLiberada(int codigoTecla) {
		if (codigoTecla == controles.teclaArriba() || codigoTecla == controles.teclaAbajo()) {
			this.asignaVelY(0);
		}
	}
	
	@Override
	public void colision(JObjeto otroObj, boolean causante) {
		if (otroObj instanceof Pelota pelota) {
			pelota.asignaVelX(-pelota.velX());
			int distanciaCentro = pelota.centro().y - this.centro().y;
			pelota.asignaVelY(distanciaCentro * MULTIPLICADOR_VELOCIDAD_BORDES);
		}
	}
}
