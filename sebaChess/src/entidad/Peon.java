package entidad;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

public class Peon extends Pieza {
	private Image imagen;

	public Peon(PiezaEnum nombre, Color color, int fila, int columna, Tablero tablero) {
		super(nombre, color, fila, columna, tablero);
		GestorRecursos gr = GestorRecursos.getInstancia();
		if (color == Color.white) {
			imagen = gr.getPeonBlanco();
		} else {
			imagen = gr.getPeonNegro();
		}

	}

	@Override
	public void pintarPieza(Graphics g, int x, int y, int lado) {

		g.drawImage(imagen, x, y, lado, lado, null);
		// Si está seleccionada, dibujamos casillas controladas
		if (seleccionada) {
			for (Integer idx : casillasControladas) {
				int filaCtrl = idx / 8;
				int colCtrl = idx % 8;

				// Resaltar casilla
				Graphics g2 = g.create();
				g2.setColor(new Color(255, 255, 0, 128)); // amarillo semitransparente
				g2.fillRect(colCtrl * lado, filaCtrl * lado, lado, lado);
				g2.dispose();
			}
		}

	}

	public boolean puedeMover(int filaDestino, int colDestino) {
    int dir = (color == Color.white) ? -1 : 1;
    int filaActual = this.fila;
    int colActual = this.columna;
    Casilla[][] casillas = tablero.getCasillas();

    // Movimiento hacia adelante simple
    if (colActual == colDestino && filaDestino == filaActual + dir) {
        return !casillas[filaDestino][colDestino].isOcupada();
    }

    // Movimiento doble desde fila inicial
    int filaInicial = (color == Color.white) ? 6 : 1;
    if (colActual == colDestino && filaActual == filaInicial && filaDestino == filaActual + 2 * dir) {
        return !casillas[filaActual + dir][colDestino].isOcupada()
            && !casillas[filaDestino][colDestino].isOcupada();
    }

    // Captura diagonal normal o al paso
    if (Math.abs(colDestino - colActual) == 1 && filaDestino == filaActual + dir) {
        // Captura normal
        if (casillas[filaDestino][colDestino].isOcupada()
            && casillas[filaDestino][colDestino].getPieza().getColor() != color) {
            return true;
        }

        // Captura al paso
        Peon peonPaso = tablero.getPeonConDerechoAlPaso();
        if (peonPaso != null
            && peonPaso.getFila() == filaActual
            && peonPaso.getColumna() == colDestino) {
            return true;
        }
    }

    return false;
}


	@Override
	public void actualizarCasillasControladas() {
		casillasControladas.clear();
		int dir = (color == Color.white) ? -1 : 1;
		int filaCaptura = fila + dir;

		if (filaCaptura >= 0 && filaCaptura < 8) {
			if (columna - 1 >= 0)
				casillasControladas.add(indice(filaCaptura, columna - 1));
			if (columna + 1 < 8)
				casillasControladas.add(indice(filaCaptura, columna + 1));
		}
	}
	
}
