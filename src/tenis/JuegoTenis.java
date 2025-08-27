package tenis;

import j2d.Juego;

public class JuegoTenis {
	public static void main(String[] args) {
		Juego.asignaNombre("Tenis");
		EscenaTenis escena = new EscenaTenis();
		Juego.anhadeEscena(escena);
		Juego.jugar();
	}
}
