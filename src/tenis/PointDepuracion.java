package tenis;

import java.awt.Point;

public class PointDepuracion extends Point {

	private static final long serialVersionUID = 1L;
	public int numSecuencia;

	public PointDepuracion(int x, int y, int numSecuencia) {
		super(x, y);
		this.numSecuencia = numSecuencia;
	}
}
