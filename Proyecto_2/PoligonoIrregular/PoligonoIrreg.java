// Proyecto 2
// Ramirez Dominguez Aldo Eduardo
// 4CM14

import java.awt.Polygon;
import java.util.Random;

public class PoligonoIrreg {
	private Coordenada[] vertices;
	private int countVertices = 0;
	private double angle;
	protected int radio;

	public PoligonoIrreg(int numVertices) {
		vertices = new Coordenada[numVertices];
		angle = 2 * Math.PI / numVertices;
		radio = (int) getRandomNumberRange(50, 300);
	}

	public void anadeVertice(Coordenada c1) {
		vertices[this.countVertices] = c1;
		this.countVertices += 1;
	}

	public Polygon getPolygon(double centroX, double centroY) {
		Polygon poligono = new Polygon();
		for (int i = 0; i < this.vertices.length; i++) {
			double x = centroX + this.radio * Math.cos(i * angle);
			double y = centroY + this.radio * Math.sin(i * angle);
			poligono.addPoint((int) x, (int) y);
		}
		return poligono;
	}

	private double getRandomNumberRange(int min, int max) {
		Random r = new Random();
		double random = min + r.nextDouble() * (max - min);
		return random;
	}

	@Override
	public String toString() {
		String str = "| X | Y |\n";
		for (int i = 0; i < countVertices; i++)
			str = str + "[" + String.valueOf(vertices[i].abcisa()) + "," + String.valueOf(vertices[i].ordenada())
					+ "]\n";
		return str;
	}
}
