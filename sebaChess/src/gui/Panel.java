package gui;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;

import entidad.Casilla;
import entidad.Pieza;
import entidad.Tablero;

public class Panel extends JPanel implements MouseListener {

	public final int WIDTH;
	public final int HEIGHT;
	public Tablero tablero;
	Pieza piezaSeleccionada;

	public Panel(int WIDTH, int HEIGHT) {
		this.WIDTH = WIDTH;
		this.HEIGHT = HEIGHT;
		piezaSeleccionada = null;
		tablero = new Tablero();
		addMouseListener(this);

	}

	public void paint(Graphics g) {
		super.paint(g);
		tablero.paint(g);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {

		int lado = tablero.getCasillas()[0][0].getLado();

		int fila = e.getY() / lado;
		int col = e.getX() / lado;

		Casilla c = tablero.getCasillas()[fila][col];

		if (piezaSeleccionada == null) {
			if (c.isOcupada()) {
				piezaSeleccionada = c.getPieza();
				piezaSeleccionada.setSeleccionada(true);

				System.out.println("Pieza seleccionada: " + piezaSeleccionada.getNombre());

			}
		} else {

			boolean puedeMover = tablero.moverPieza(piezaSeleccionada, fila, col);
			if (puedeMover) {
				System.out.println("pieza movida a: " + fila + ", " + col);
			} else {
				System.out.println("movimiento invalido");
			}
			// Deseleccionamos siempre después de click, aunque no se mueva
			piezaSeleccionada.setSeleccionada(false);
			piezaSeleccionada = null;

		}
		// Actualizamos todas las casillas controladas **una sola vez**
		for (Pieza p : tablero.getPiezas()) {
			p.actualizarCasillasControladas();
		}

		repaint();
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

}
