package entidad;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

public class Caballo extends Pieza{
    private Image imagen;
    public Caballo(PiezaEnum nombre, Color color, int fila, int columna,Tablero tablero) {
        super(nombre, color, fila, columna,tablero);
       GestorRecursos gr=GestorRecursos.getInstancia();
        if(color==Color.white){
            imagen=gr.getCaballoBlanco();
        }else{
            imagen=gr.getCaballoNegro();
        }
    }

    @Override
    public void pintarPieza(Graphics g, int x, int y, int lado) {
          g.drawImage(imagen, x, y, lado, lado, null);
    }

    @Override
    public boolean puedeMover(int filaDestino, int colDestino) {
       int distanciaF=Math.abs(filaDestino-fila);
       int distanciaC=Math.abs(colDestino-columna);

        if(!((distanciaF==2 && distanciaC ==1) || (distanciaF == 1 && distanciaC == 2))){
            return false;
        }

        if(!tablero.getCasillas()[filaDestino][colDestino].isOcupada()){
            return true;
        }
        return tablero.getCasillas()[filaDestino][colDestino].getPieza().getColor() != this.getColor();
    }

   @Override
    public void actualizarCasillasControladas() {
        casillasControladas.clear();
        int[][] movimientos = {
            { -2, -1 }, { -2, 1 }, { -1, -2 }, { -1, 2 },
            { 1, -2 }, { 1, 2 }, { 2, -1 }, { 2, 1 }
        };

        for (int[] m : movimientos) {
            int f = fila + m[0];
            int c = columna + m[1];

            if (f >= 0 && f < 8 && c >= 0 && c < 8) {
                casillasControladas.add(indice(f, c));
            }
        }
    }

}
