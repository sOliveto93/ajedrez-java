package entidad;

import java.awt.Color;
import java.awt.Graphics;
import java.util.HashSet;
import java.util.Set;

public abstract class Pieza {
	protected PiezaEnum nombre;
	protected Color color;
	protected int fila;
	protected int columna;
	protected Tablero tablero;
	protected Set<Integer> casillasControladas = new HashSet<>();
	protected boolean seleccionada = false;

	public Pieza(PiezaEnum nombre, Color color, int fila, int columna, Tablero tablero) {
		this.nombre = nombre;
		this.color = color;
		this.fila = fila;
		this.columna = columna;
		this.tablero = tablero;
	}

	public abstract void pintarPieza(Graphics g, int x, int y, int lado);

	public abstract boolean puedeMover(int filaDestino, int colDestino);

	public abstract void actualizarCasillasControladas();

	protected int indice(int fila, int col) {
		return fila * 8 + col;
	}

	public String getNombre() {
		return this.nombre.toString();
	}

	public Color getColor() {
		return color;
	}

	public int getFila() {
		return fila;
	}

	public int getColumna() {
		return columna;
	}

	public void setFila(int fila) {
		this.fila = fila;
	}

	public void setColumna(int col) {
		this.columna = col;
	}

	public Set<Integer> getCasillasControladas() {
		return casillasControladas;
	}

	public boolean isSeleccionada() {
		return seleccionada;
	}

	public void setSeleccionada(boolean sel) {
		seleccionada = sel;
	}
}
