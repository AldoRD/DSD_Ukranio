public class PruebaRectangulo {
    public static void main (String[] args) {

        // Rectangulo rect1 = new Rectangulo(2,3,5,1);
        // Objetos Coordenada
        Coordenada c1 = new Coordenada(5, 10);
        Coordenada c2 = new Coordenada(8,5);
        // Utilizando el nuevo construtor
        Rectangulo rect1 = new Rectangulo(c1,c2);

        rect1.area();
        rect1.imprimePerimetro();
    }
}