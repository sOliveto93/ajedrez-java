package entidad;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

public class Rey extends Pieza {

	private Image imagen;
	private boolean haMovido;

	public Rey(PiezaEnum nombre, Color color, int fila, int columna, Tablero tablero) {
		super(nombre, color, fila, columna, tablero);
		haMovido=false;
		GestorRecursos gr = GestorRecursos.getInstancia();
		if (color == Color.white) {
			imagen = gr.getReyBlanco();
		} else {
			imagen = gr.getReyNegro();
		}

	}

	@Override
	public void pintarPieza(Graphics g, int x, int y, int lado) {
		g.drawImage(imagen, x, y, lado, lado, null);
	}

	@Override
public boolean puedeMover(int filaDestino, int colDestino) {
    boolean enroqueExitoso = false;

    // Intento de enroque
    if (filaDestino == fila && Math.abs(colDestino - columna) == 2) {
        enroqueExitoso = puedeEnrocar(colDestino);
    }

    // Validación normal de 1 casilla
    int distanciaF = Math.abs(filaDestino - fila);
    int distanciaC = Math.abs(colDestino - columna);

    boolean movimientoNormal = false;
    if (distanciaF <= 1 && distanciaC <= 1) {
        Casilla destino = tablero.getCasillas()[filaDestino][colDestino];

        if (!tablero.casillaBajoAtaque(filaDestino, colDestino, color)) {
            if (!destino.isOcupada()) {
                movimientoNormal = true;
            } else if (destino.getPieza().getColor() != color &&
                       tablero.contarAtacantes(filaDestino, colDestino, color) < 2) {
                movimientoNormal = true;
            }
        }
    }

    // Devuelve true si es un movimiento normal válido o un enroque posible
		System.out.println("movimiento normal-> "+movimientoNormal+" enroque ->"+enroqueExitoso);
    return movimientoNormal || enroqueExitoso;
}

	@Override
	public void actualizarCasillasControladas() {
		casillasControladas.clear();
		for (int i = fila - 1; i <= fila + 1; i++) {
			for (int j = columna - 1; j <= columna + 1; j++) {
				if (i >= 0 && i < 8 && j >= 0 && j < 8) {
					casillasControladas.add(indice(i, j));
				}
			}
		}
	}

	public boolean puedeEnrocar(int colDestino){
		
		if(haMovido || tablero.estaEnJaque(color)){
			return false;
		}
		int colTorre=(colDestino>columna)? 7 : 0;
		Pieza torre=tablero.getCasillas()[fila][colTorre].getPieza();

		if(!(torre instanceof Torre) || ((Torre) torre).getHaMovido()){return false;}

		int casillaInicio=Math.min(columna,colTorre)+1;
		int casillaFinal=Math.max(columna,colTorre)-1;

		for(int c=casillaInicio;c<casillaFinal;c++){
			if(tablero.getCasillas()[fila][c].isOcupada()){return false;}
			if(tablero.casillaBajoAtaque(fila,c, color)){return false;}
		}

		return true;
	}





	public boolean getHaMovido() {
    return haMovido;
	}

	public void setHaMovido(boolean haMovido) {
		this.haMovido = haMovido;
	}

}
