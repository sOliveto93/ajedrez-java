package entidad;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

public class Tablero {
	private Casilla[][] casillas;
	private List<Pieza> piezas;
	private Peon peonConDerechoAlPaso = null;

	public Tablero() {
		casillas = new Casilla[8][8];
		piezas = new ArrayList<Pieza>();
		iniciarTableroVacio();
		inicializarPiezas();

	}

	public void iniciarTableroVacio() {
		Color color = null;
		for (int i = 0; i < casillas.length; i++) {
			for (int j = 0; j < casillas[i].length; j++) {

				if (i % 2 != 0) {
					color = (j % 2 == 0) ? Color.GREEN : Color.WHITE;
				} else {
					color = (j % 2 == 0) ? Color.WHITE : Color.GREEN;
				}
				casillas[i][j] = new Casilla(color);
			}
		}
	}

	public void inicializarPiezas() {

		for (int col = 0; col < 8; col++) {
			Peon peonW = new Peon(PiezaEnum.PEON, Color.white, 6, col, this);
			piezas.add(peonW);
			casillas[6][col].setPieza(peonW);
			Peon peonN = new Peon(PiezaEnum.PEON, Color.black, 1, col, this);
			piezas.add(peonN);
			casillas[1][col].setPieza(peonN);
		}

		Torre t1W = new Torre(PiezaEnum.TORRE, Color.white, 7, 0, this);
		Torre t2W = new Torre(PiezaEnum.TORRE, Color.white, 7, 7, this);
		piezas.add(t1W);
		piezas.add(t2W);
		Torre t1N = new Torre(PiezaEnum.TORRE, Color.black, 0, 0, this);
		Torre t2N = new Torre(PiezaEnum.TORRE, Color.black, 0, 7, this);
		piezas.add(t1N);
		piezas.add(t2N);
		casillas[7][0].setPieza(t1W);
		casillas[7][7].setPieza(t2W);
		casillas[0][0].setPieza(t1N);
		casillas[0][7].setPieza(t2N);

		Caballo c1w = new Caballo(PiezaEnum.CABALLO, Color.white, 7, 1, this);
		Caballo c2w = new Caballo(PiezaEnum.CABALLO, Color.white, 7, 6, this);
		piezas.add(c1w);
		piezas.add(c2w);
		Caballo c1n = new Caballo(PiezaEnum.CABALLO, Color.black, 0, 1, this);
		Caballo c2n = new Caballo(PiezaEnum.CABALLO, Color.black, 0, 6, this);
		piezas.add(c1n);
		piezas.add(c2n);
		casillas[7][1].setPieza(c1w);
		casillas[7][6].setPieza(c2w);
		casillas[0][1].setPieza(c1n);
		casillas[0][6].setPieza(c2n);

		Alfil a1w = new Alfil(PiezaEnum.ALFIL, Color.white, 7, 2, this);
		Alfil a2w = new Alfil(PiezaEnum.ALFIL, Color.white, 7, 5, this);
		piezas.add(a1w);
		piezas.add(a2w);
		Alfil a1n = new Alfil(PiezaEnum.ALFIL, Color.black, 0, 2, this);
		Alfil a2n = new Alfil(PiezaEnum.ALFIL, Color.black, 0, 5, this);
		piezas.add(a1n);
		piezas.add(a2n);
		casillas[7][2].setPieza(a1w);
		casillas[7][5].setPieza(a2w);
		casillas[0][2].setPieza(a1n);
		casillas[0][5].setPieza(a2n);

		Dama damaW = new Dama(PiezaEnum.DAMA, Color.white, 7, 3, this);
		Dama damaN = new Dama(PiezaEnum.DAMA, Color.black, 0, 3, this);
		piezas.add(damaW);
		piezas.add(damaN);
		casillas[7][3].setPieza(damaW);
		casillas[0][3].setPieza(damaN);

		Rey reyW = new Rey(PiezaEnum.REY, Color.white, 7, 4, this);
		Rey reyN = new Rey(PiezaEnum.REY, Color.black, 0, 4, this);
		piezas.add(reyW);
		piezas.add(reyN);
		casillas[7][4].setPieza(reyW);
		casillas[0][4].setPieza(reyN);

	}

	public void paint(Graphics g) {
		// 1 Pinta fondo de todas las casillas
		for (int i = 0; i < casillas.length; i++) {
			for (int j = 0; j < casillas[i].length; j++) {
				casillas[i][j].pintarCasilla(g, i, j);
			}
		}

		// 2 Pinta los resaltes de las piezas seleccionadas
		for (Pieza p : piezas) {
			if (p.isSeleccionada()) {
				int lado = casillas[0][0].getLado();
				for (Integer idx : p.getCasillasControladas()) {
					int f = idx / 8;
					int c = idx % 8;
					Graphics g2 = g.create();
					g2.setColor(new Color(255, 255, 0, 128));
					g2.fillRect(c * lado, f * lado, lado, lado);
					g2.dispose();
				}
			}
		}

		// Pinta todas las piezas encima
		for (Pieza p : piezas) {
			int x = p.getColumna() * casillas[0][0].getLado();
			int y = p.getFila() * casillas[0][0].getLado();
			p.pintarPieza(g, x, y, casillas[0][0].getLado());
		}
	}

	public boolean moverPieza(Pieza pieza, int filaDestino, int colDestino) {
    if (pieza == null)
        return false;

    if (filaDestino < 0 || filaDestino >= 8 || colDestino < 0 || colDestino >= 8)
        return false;

    // Enroque
    if (pieza instanceof Rey && Math.abs(colDestino - pieza.getColumna()) == 2) {
        Rey rey = (Rey) pieza;
        if (rey.puedeEnrocar(colDestino)) {
            realizarEnroque(rey, colDestino);
            return true;
        }
    }

    if (!pieza.puedeMover(filaDestino, colDestino))
        return false;

    Casilla origen = casillas[pieza.getFila()][pieza.getColumna()];
    Casilla destino = casillas[filaDestino][colDestino];
    Pieza piezaCapturada = destino.getPieza();

    int filaOriginal = pieza.getFila();
    int colOriginal = pieza.getColumna();

    // Simular movimiento para verificar jaque
    pieza.setFila(filaDestino);
    pieza.setColumna(colDestino);
    boolean jaque = estaEnJaque(pieza.getColor());
    pieza.setFila(filaOriginal);
    pieza.setColumna(colOriginal);
    if (jaque)
        return false;

    // peon al paso
    if (pieza instanceof Peon peon) {
       
        if (peonConDerechoAlPaso != null) {

            if (Math.abs(peon.getColumna() - peonConDerechoAlPaso.getColumna()) == 1 &&
                peon.getFila() == peonConDerechoAlPaso.getFila()) {
                
                int dir = (peon.getColor() == Color.white) ? -1 : 1;
                if (filaDestino == peon.getFila() + dir && colDestino == peonConDerechoAlPaso.getColumna()) {
                    
                    casillas[peonConDerechoAlPaso.getFila()][peonConDerechoAlPaso.getColumna()].liberarCasilla();
                    piezas.remove(peonConDerechoAlPaso);

                    origen.liberarCasilla();
                    destino.setPieza(peon);
                    peon.setFila(filaDestino);
                    peon.setColumna(colDestino);

                    peonConDerechoAlPaso = null;
                    return true;
                }
            }
        }

      //siempre que movemos 2 al peon activamos la posibilidad de derecho al paso
        int dir = (peon.getColor() == Color.white) ? -1 : 1;
        int filaInicial = (peon.getColor() == Color.white) ? 6 : 1;
        if (filaOriginal == filaInicial && filaDestino == filaInicial + 2 * dir) {
            peonConDerechoAlPaso = peon;
        } else {
            peonConDerechoAlPaso = null;
        }
    } else {
        peonConDerechoAlPaso = null;
    }

    // Movimiento normal
    origen.liberarCasilla();
    if (piezaCapturada != null)
        piezas.remove(piezaCapturada);

    destino.setPieza(pieza);
    pieza.setFila(filaDestino);
    pieza.setColumna(colDestino);

    return true;
}



	public boolean estaEnJaque(Color colorRey) {
		Rey rey = null;

		// Buscamos al rey del color dado
		for (Pieza p : piezas) {
			if (p instanceof Rey && p.getColor().equals(colorRey)) {
				rey = (Rey) p;
				break;
			}
		}
		if (rey == null)
			return false;

		int idxRey = rey.getFila() * 8 + rey.getColumna();

		// Revisamos si alguna pieza enemiga controla la casilla del rey
		for (Pieza p : piezas) {
			if (!p.getColor().equals(colorRey)) {
				p.actualizarCasillasControladas(); // asegurarse que estén actualizadas
				if (p.getCasillasControladas().contains(idxRey)) {
					System.out.println("En jaque el rey");
					return true;
				}
			}
		}

		return false;
	}

	public boolean casillaBajoAtaque(int fila, int col, Color colorAliado) {
		int idx = fila * 8 + col;
		for (Pieza p : piezas) {
			if (p.getColor() != colorAliado) {
				p.actualizarCasillasControladas(); // aseguramos control actualizado
				if (p.getCasillasControladas().contains(idx)) {
					return true;
				}
			}
		}
		return false;
	}

	public int contarAtacantes(int fila, int col, Color colorAliado) {
		int idx = fila * 8 + col;
		int contador = 0;
		for (Pieza p : piezas) {
			if (p.getColor() != colorAliado && p.getCasillasControladas().contains(idx)) {
				contador++;
			}
		}
		return contador;
	}

	public void realizarEnroque(Rey rey,int colDestino){
		int fila=rey.getFila();
		int colOrigen=rey.getColumna();

		boolean enroqueCorto=colDestino>colOrigen;
		int colTorre=enroqueCorto?7:0;
		Torre torre =(Torre) casillas[fila][colTorre].getPieza();
		if(torre==null){return;}//por seguridad

		int nuevaColRey=enroqueCorto ? colOrigen+2:colOrigen-2;
		casillas[fila][colOrigen].liberarCasilla();
		casillas[fila][colDestino].setPieza(rey);
		rey.setColumna(nuevaColRey);
		rey.setHaMovido(true);

		int nuevaColTorre=enroqueCorto?nuevaColRey-1:nuevaColRey+1;
		casillas[fila][colTorre].liberarCasilla();
		casillas[fila][nuevaColTorre].setPieza(torre);
		torre.setColumna(nuevaColTorre);
		torre.setHaMovido(true);

	}

	public Casilla[][] getCasillas() {
		return this.casillas;
	}

	public List<Pieza> getPiezas() {
		return this.piezas;
	}

   public Peon getPeonConDerechoAlPaso() {
    return peonConDerechoAlPaso;
}
}
