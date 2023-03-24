import java.util.Random;

public class PruebaPoligono {
    
    public static void main (String[] args) {
        int numVertices = 10;
        PoligonoIrreg poligono = new PoligonoIrreg(); 

        for(int i = 0 ; i < numVertices; i++){
        	Double x = getRandomNumberRange(-100,100);
        	Double y = getRandomNumberRange(-100,100);

        	System.out.println(i + " - x: " + x + " y: " + y);
        	poligono.anadeVertice(new Coordenada(x,y));
        }

        System.out.println("Coordenadas del poligono irregular:");
        System.out.println(poligono.toString());
    }

    public static double getRandomNumberRange(int min, int max){
        Random r = new Random();
        double random = min + r.nextDouble() * (max - min);
        return random;
    }
}
