public class Coordenada implements java.io.Serializable {

    private double x, y;

    public Coordenada() {
    };

    public Coordenada(double x, double y) {
        this.x = x;
        this.y = y;
    }

    // Metodo getter de x
    public double abcisa() {
        return x;
    }

    // Metodo getter de y
    public double ordenada() {
        return y;
    }

    // Metodo setter de x
    public void setx(double x) {
        this.x = x;
    }

    // Metodo setter de y
    public void sety(double y) {
        this.y = y;
    }

    // Sobreescritura del método de la superclase objeto para imprimir con
    // System.out.println( )
    @Override
    public String toString() {
        return "[" + x + "," + y + "]";
    }

}