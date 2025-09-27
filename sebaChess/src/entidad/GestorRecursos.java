package entidad;

import java.awt.Image;
import java.io.IOException;

import javax.imageio.ImageIO;

public class GestorRecursos {
	public static GestorRecursos instancia;

	private Image peonBlanco;
	private Image torreBlanca;
	private Image alfilBlanco;
	private Image caballoBlanco;
	private Image damaBlanca;
	private Image reyBlanco;

	// Imágenes de piezas negras
	private Image peonNegro;
	private Image torreNegra;
	private Image alfilNegro;
	private Image caballoNegro;
	private Image damaNegra;
	private Image reyNegro;

	private GestorRecursos() {
		try {
			peonBlanco = ImageIO.read(getClass().getResource("/recursos/white-pawn.png"));
			torreBlanca = ImageIO.read(getClass().getResource("/recursos/white-rook.png"));
			alfilBlanco = ImageIO.read(getClass().getResource("/recursos/white-bishop.png"));
			caballoBlanco = ImageIO.read(getClass().getResource("/recursos/white-knight.png"));
			damaBlanca = ImageIO.read(getClass().getResource("/recursos/white-queen.png"));
			reyBlanco = ImageIO.read(getClass().getResource("/recursos/white-king.png"));

			peonNegro = ImageIO.read(getClass().getResource("/recursos/black-pawn.png"));
			torreNegra = ImageIO.read(getClass().getResource("/recursos/black-rook.png"));
			alfilNegro = ImageIO.read(getClass().getResource("/recursos/black-bishop.png"));
			caballoNegro = ImageIO.read(getClass().getResource("/recursos/black-knight.png"));
			damaNegra = ImageIO.read(getClass().getResource("/recursos/black-queen.png"));
			reyNegro = ImageIO.read(getClass().getResource("/recursos/black-king.png"));

		} catch (IOException e) {
			System.out.println("Error al cargar las imagenes: " + e.getMessage());
		}
	}

	public static GestorRecursos getInstancia() {
		if (instancia == null) {
			instancia = new GestorRecursos();
		}
		return instancia;
	}

	public Image getPeonBlanco() {
		return peonBlanco;
	}

	public Image getPeonNegro() {
		return peonNegro;
	}

	public Image getTorreBlanco() {
		return torreBlanca;
	}

	public Image getTorreNegra() {
		return torreNegra;
	}

	public Image getAlfilBlanco() {
		return alfilBlanco;
	}

	public Image getAlfilNegro() {
		return alfilNegro;
	}

	public Image getCaballoBlanco() {
		return caballoBlanco;
	}

	public Image getCaballoNegro() {
		return caballoNegro;
	}

	public Image getDamaBlanca() {
		return damaBlanca;
	}

	public Image getDamaNegra() {
		return damaNegra;
	}

	public Image getReyBlanco() {
		return reyBlanco;
	}

	public Image getReyNegro() {
		return reyNegro;
	}
}
