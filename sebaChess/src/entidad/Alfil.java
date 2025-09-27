package entidad;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

public class Alfil extends Pieza {
	private Image imagen;

	public Alfil(PiezaEnum nombre, Color color, int fila, int columna, Tablero tablero) {
		super(nombre, color, fila, columna, tablero);
		GestorRecursos gr = GestorRecursos.getInstancia();
		if (color == Color.white) {
			imagen = gr.getAlfilBlanco();
		} else {
			imagen = gr.getAlfilNegro();
		}
	}

	@Override
	public void pintarPieza(Graphics g, int x, int y, int lado) {
		g.drawImage(imagen, x, y, lado, lado, null);
	}

	public boolean puedeMover(int filaDestino, int colDestino) {
		// verificamos que sea movimiento diagonal
		if (Math.abs(filaDestino - fila) != Math.abs(colDestino - columna)) {
			return false;
		}

		int dirFila = (filaDestino > fila) ? 1 : -1;
		int dirCol = (colDestino > columna) ? 1 : -1;

		int f = fila + dirFila;
		int c = columna + dirCol;

		while (f != filaDestino || c != colDestino) {
			if (tablero.getCasillas()[f][c].isOcupada()) {
				return false;
			}
			f += dirFila;
			c += dirCol;
		}

		if (!tablero.getCasillas()[filaDestino][colDestino].isOcupada()) {
			return true;
		}
		// llegamos si esta ocupada y averiguamos por que pieza
		return tablero.getCasillas()[filaDestino][colDestino].getPieza().getColor() != this.getColor();

	}

	@Override
	public void actualizarCasillasControladas() {
		casillasControladas.clear();

		int[][] direcciones = { { 1, 1 }, { 1, -1 }, { -1, 1 }, { -1, -1 } };

		for (int[] dir : direcciones) {
			int f = fila + dir[0];
			int c = columna + dir[1];

			while (f >= 0 && f < 8 && c >= 0 && c < 8) {
				casillasControladas.add(indice(f, c));

				if (tablero.getCasillas()[f][c].isOcupada()) {
					Pieza p = tablero.getCasillas()[f][c].getPieza();
					// Rompe si es cualquier pieza que no sea rey enemigo
					if (!(p instanceof Rey) || p.getColor() == this.color) {
						break;
					}
				}
				f += dir[0];
				c += dir[1];
			}
		}
	}

}
