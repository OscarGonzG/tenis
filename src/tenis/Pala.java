package tenis;

import java.awt.Color;
import java.awt.Point;
import java.awt.event.KeyEvent;

import j2d.IControladoTeclado;
import j2d.JObjeto;
import j2d.JObjetoRectangulo;
import j2d.Juego;
import j2d.mods.multijugador.VariableRed;

/**
 * Pala en el juego de tenis.
 *  
 * @author Óscar González García
 * @version abr-2026
 */
public class Pala extends JObjetoRectangulo implements IControladoTeclado {
	
	private static final int ALTURA_BASE = 75;
	private static final int ANCHURA_BASE = 5;
	public static final int VELOCIDAD_BASE = 8;
	
	private static final float MULTIPLICADOR_VELOCIDAD_BORDES = 0.1f;
	
	private final VariableRed<Integer> posY;

	/**
	 * Construye una pala.
	 * @param nombre nombre del JObjeto.
	 */
	public Pala(String nombre) {
		super(nombre, ANCHURA_BASE, ALTURA_BASE, Color.WHITE);
		posY = Juego.nuevaVariableRed(Integer.class, nombre + ".posY", 0);
		if (Juego.esCliente()) {
			posY.anhadeSuscriptor(v -> {
				this.posiciona(new Point(posicion().x, v.valor()));
			});
		}
	}
	
	@Override
	public void ciclo() {
		if (Juego.esServidor()) {
			posY.asignaValor(posicion().y);
		}
	}

	@Override
	public void teclaPresionada(int codigoTecla) {
		if (codigoTecla == KeyEvent.VK_UP || codigoTecla == KeyEvent.VK_W) {
			this.asignaVelY(-VELOCIDAD_BASE);
		} else if (codigoTecla == KeyEvent.VK_DOWN || codigoTecla == KeyEvent.VK_S) {
			this.asignaVelY(VELOCIDAD_BASE);
		}
	}

	@Override
	public void teclaLiberada(int codigoTecla) {
		if (codigoTecla == KeyEvent.VK_UP || codigoTecla == KeyEvent.VK_W
				|| codigoTecla == KeyEvent.VK_DOWN || codigoTecla == KeyEvent.VK_S) {
			this.asignaVelY(0);
		}
	}
	
	@Override
	public void colision(JObjeto otroObj, boolean causante) {
		if (otroObj instanceof Pelota pelota) {
			System.out.println("Pelota devuelta");
			pelota.asignaVelX(-pelota.velX());
			int distanciaCentro = pelota.centro().y - this.centro().y;
			pelota.asignaVelY(distanciaCentro * MULTIPLICADOR_VELOCIDAD_BORDES);
		}
	}
}
