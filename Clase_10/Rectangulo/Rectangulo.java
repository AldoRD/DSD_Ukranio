public class Rectangulo extends Figura implements Perimetro{
    private Coordenada superiorIzq, inferiorDer;

    public Rectangulo(){
        this.numeroLados = 4;
        superiorIzq = new Coordenada(0,0);
        inferiorDer = new Coordenada(0,0);
    }

    public Rectangulo(Coordenada c1, Coordenada c2){
        this.numeroLados = 4;
        superiorIzq = new Coordenada(c1.abcisa(),c1.ordenada());
        inferiorDer = new Coordenada(c2.abcisa(),c2.ordenada());
    }

    public Rectangulo(double xSupIzq, double ySupIzq, double xInfDer, double yInfDer){
        this.numeroLados = 4;
        superiorIzq = new Coordenada(xSupIzq, ySupIzq);
        inferiorDer = new Coordenada(xInfDer, yInfDer);        
    }

    //Metodo getter de la coordenada superior izquierda
    public Coordenada superiorIzquierda( ) { return superiorIzq; }

    //Metodo getter de la coordenada inferior derecha
    public Coordenada inferiorDerecha( ) { return inferiorDer; }

    @Override
    public void area(){
        double alto = superiorIzq.ordenada() - inferiorDer.ordenada();
        double ancho = inferiorDer.abcisa() - superiorIzq.abcisa();
        double area = alto * ancho;
        System.out.println("La figura de "+ this.numeroLados + " lados, tiene un area de: " + area);
    }

    //Sobreescritura del método de la superclase objeto para imprimir con System.out.println( )
    @Override
    public String toString( ) {
        return "Esquina superior izquierda: " + superiorIzq + "\tEsquina superior derecha:" + inferiorDer + "\n";
    }

    @Override
    public float imprimePerimetro() {
        double alto = superiorIzq.ordenada() - inferiorDer.ordenada();
        double ancho = inferiorDer.abcisa() - superiorIzq.abcisa();
        float perimetro = (float)(alto * 2 + ancho * 2);
        System.out.println("El perimetro de la figura es: " + perimetro);
        return perimetro;
    }
}