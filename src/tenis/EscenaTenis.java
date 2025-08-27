package tenis;

import java.awt.Color;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.util.Random;

import j2d.JEscena;
import j2d.JObjetoRectangulo;
import j2d.Juego;
import j2d.mods.JObjetoVisNumTexto;


/**
 * Representa la escena del juego de tenis.
 */
public class EscenaTenis extends JEscena {
	
	private final Jugador jugadorIzq;
	private final Jugador jugadorDer;
	private final Pelota pelota;
	
	private static final int SEPARACION_PALA = 60;
	private static final int RADIO_PELOTA = 10;
	private static final int VELOCIDAD_PELOTA = 12;
	
	private static final int SEPARACION_PARED_SUP_PUNTUACION = 70;
	private static final int SEPARACION_PARED_INF_PUNTUACION = 150;
	private static final int TAMANHO_FUENTE = 32;
	
	private static final int ANCHO_RED = 4;
	private static final int NUM_SEGMENTOS_RED = 10;
	
	
	private static final Random rand = new Random();
	
	/**
	 * Crea una EscenaTenis instanciando todos los objetos necesarios para el juego.
	 */
	public EscenaTenis() {
		poneFondo(Color.BLACK);
		
		creaBordes(Color.BLACK);
		
		// Pelota
		this.pelota = new Pelota("pelota", RADIO_PELOTA);
		incluyeObjCentrado(pelota, Juego.anchoPixelsX() / 2, Juego.altoPixelsY() / 2);
		pelota.asignaVelX(VELOCIDAD_PELOTA);
		
		// Contadores
		JObjetoVisNumTexto contadorIzq = new JObjetoVisNumTexto("", 0);
		JObjetoVisNumTexto contadorDer = new JObjetoVisNumTexto("", 0);
		contadorIzq.asignaFuente(Font.DIALOG, Font.BOLD, TAMANHO_FUENTE);
		contadorDer.asignaFuente(Font.DIALOG, Font.BOLD, TAMANHO_FUENTE);
		
		contadorIzq.asignaColor(Color.WHITE);
		contadorDer.asignaColor(Color.WHITE);
		
		incluyeObjCentrado(contadorIzq, SEPARACION_PARED_INF_PUNTUACION, SEPARACION_PARED_SUP_PUNTUACION);
		incluyeObjCentrado(contadorDer, Juego.anchoPixelsX() - SEPARACION_PARED_INF_PUNTUACION, SEPARACION_PARED_SUP_PUNTUACION);
		
		
		
		// Jugadores
		this.jugadorIzq = new Jugador(contadorIzq);
		this.jugadorDer = new Jugador(contadorDer);
		
		
		// Palas
		Controles controlesIzq = new Controles(KeyEvent.VK_W, KeyEvent.VK_S);
		Controles controlesDer = new Controles(KeyEvent.VK_UP, KeyEvent.VK_DOWN);
		
		Pala palaIzq = new Pala("pala1", controlesIzq);
		Pala palaDer = new Pala("pala2", controlesDer);
		
		controladoTecladoAnhade(palaIzq);
		controladoTecladoAnhade(palaDer);
		
		incluyeObjCentrado(palaIzq, SEPARACION_PALA, Juego.altoPixelsY() / 2);
		incluyeObjCentrado(palaDer, Juego.anchoPixelsX() - SEPARACION_PALA,
							Juego.altoPixelsY() / 2);
		generaRed();
	}

	/**
	 * Posiciona la pelota en el centro de la escena y le asigna su velocidad por
	 * defecto en un sentido aleatorio del eje X.
	 */
	public void reiniciaPelota() {
		pelota.posicionaCentro(new Point(Juego.anchoPixelsX() / 2,
								Juego.altoPixelsY() / 2));
		if (rand.nextBoolean()) {
			pelota.asignaVel(VELOCIDAD_PELOTA, 0);
		} else {
			pelota.asignaVel(-VELOCIDAD_PELOTA, 0);
		}
	}
	
	/**
	 * Crea la red del centro de la pantalla.
	 */
	private void generaRed() {
		int altoSegmento = Juego.altoPixelsY() / (NUM_SEGMENTOS_RED * 2 - 1);
		for (int i = 0; i < NUM_SEGMENTOS_RED; i++) {
			JObjetoRectangulo segmento = new JObjetoRectangulo("segmento" + i, ANCHO_RED, altoSegmento, Color.WHITE);
			segmento.colisionador().desactiva();
			
			incluyeObj(segmento, (Juego.anchoPixelsX() / 2) - (ANCHO_RED / 2), 2 * i * altoSegmento);
		}
	}
	
	public Jugador getJugadorIzq() {
		return jugadorIzq;
	}
	
	public Jugador getJugadorDer() {
		return jugadorDer;
	}
}
