package entidad;

import java.awt.Color;
import java.awt.Graphics;

public class Casilla {
	private Pieza pieza;
	private boolean ocupada;
	private Color color;
	private int lado = 64;

	public Casilla(Color color) {
		this.pieza = null;
		this.ocupada = false;
		this.color = color;
	}

	public Pieza getPieza() {
		return this.pieza;
	}

	public void setPieza(Pieza p) {
		this.pieza = p;
		ocupada = true;
	}

	public void liberarCasilla() {
		this.pieza = null;
		ocupada = false;
	}

	public boolean isOcupada() {
		return this.ocupada;
	}

	public void setOcupada(boolean o) {
		this.ocupada = o;
	}

	public Color getColor() {
		return this.color;
	}

	public void SetColor(Color color) {
		this.color = color;
	}

	public int getLado() {
		return this.lado;
	}

	// Casilla: solo dibuja el fondo
	public void pintarCasilla(Graphics g, int fila, int col) {
		int x = col * lado;
		int y = fila * lado;
		g.setColor(color);
		g.fillRect(x, y, lado, lado);
	}

}
