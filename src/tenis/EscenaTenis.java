package tenis;

import java.awt.Color;
import java.awt.Point;
import java.nio.ByteBuffer;
import java.util.Random;

import j2d.JEscena;
import j2d.JObjetoRectangulo;
import j2d.Juego;
import j2d.mods.multijugador.IReceptorEventosRed;
import j2d.mods.multijugador.ISerializador;

/**
 * Representa la escena del juego de tenis.
 *  
 * @author Óscar González García
 * @version mar-2026
 */
public class EscenaTenis extends JEscena {
	
	private Marcador marcadorIzq;
	private Marcador marcadorDer;
	private Pelota pelota;
	
	private static final int SEPARACION_PALA = 60;
	private static final int RADIO_PELOTA = 10;
	private static final int VELOCIDAD_PELOTA = 12;
	
	private static final int SEPARACION_PARED_SUP_PUNTUACION = 70;
	private static final int SEPARACION_PARED_INF_PUNTUACION = 150;
	
	private static final int ANCHO_RED = 4;
	private static final int NUM_SEGMENTOS_RED = 10;
	
	
	private static final Random rand = new Random();
	
	/**
	 * Crea una EscenaTenis instanciando todos los objetos necesarios para el juego.
	 */
	public EscenaTenis() {
		poneFondo(Color.BLACK);
		creaBordes(Color.BLACK);		
		generaRed();
	}

	@Override
	public void entraEscena() {
		ISerializador<PointDepuracion> serializador = 
				new ISerializador<PointDepuracion>() {
			@Override
			public void serializar(ByteBuffer buf, PointDepuracion obj) {
				buf.putInt(obj.x);
				buf.putInt(obj.y);
				buf.putInt(obj.numSecuencia);
			}
			
			@Override
			public PointDepuracion deserializar(ByteBuffer buf) {
				return new PointDepuracion(buf.getInt(), buf.getInt(), buf.getInt());
			}
		};
		Juego.gestorMultijugador().registroSerializables()
			.registraTipo(PointDepuracion.class, serializador);
		
		Juego.anhadeReceptorEventosRed(new IReceptorEventosRed() {
			@Override
			public void jugadorUnido(int numJugador) {				
				if (numJugador == 1) {
					marcadorIzq = new Marcador("marcador0");
					incluyeObjCentrado(marcadorIzq, SEPARACION_PARED_INF_PUNTUACION,
							SEPARACION_PARED_SUP_PUNTUACION);
					Pala pala = new Pala("pala1");
					incluyeObjCentrado(pala, SEPARACION_PALA, Juego.altoPixelsY() / 2);
					controladoTecladoRedAnhade(pala, 0);
					
					marcadorDer = new Marcador("marcador1");
					incluyeObjCentrado(marcadorDer, 
							Juego.anchoPixelsX() - SEPARACION_PARED_INF_PUNTUACION,
							SEPARACION_PARED_SUP_PUNTUACION);
					
					pala = new Pala("pala2");
					incluyeObjCentrado(pala, Juego.anchoPixelsX() - SEPARACION_PALA,
							Juego.altoPixelsY() / 2);
					controladoTecladoRedAnhade(pala, 1);
					
					pelota = new Pelota("pelota", RADIO_PELOTA);
					incluyeObjCentrado(pelota, Juego.anchoPixelsX() / 2, Juego.altoPixelsY() / 2);
					pelota.asignaVelX(VELOCIDAD_PELOTA);
				}
			}
		});
	}
	
	/**
	 * Posiciona la pelota en el centro de la escena y le asigna su velocidad por
	 * defecto en un sentido aleatorio del eje X.
	 */
	public void reiniciaPelota() {
		if (Juego.esCliente()) return;
		
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
	
	public Marcador getMarcadorIzq() {
		return marcadorIzq;
	}
	
	public Marcador getMarcadorDer() {
		return marcadorDer;
	}
}
