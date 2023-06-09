// Proyecto 2
// Ramirez Dominguez Aldo Eduardo
// 4CM14

public class PoligonoReg extends PoligonoIrreg {
	private double angle;
	private int numVertices;

	public PoligonoReg(int numLados) {
		super(numLados);
		this.angle = Math.PI / numLados;
		this.numVertices = numLados;
	}

	public double obtieneArea() {
		double lado = 2 * radio * Math.sin(angle);
		double apotema = radio * Math.cos(angle);
		double perimetro = lado * numVertices;
		double area = (perimetro * apotema) / 2;
		return area;
	}

}
