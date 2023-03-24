import java.util.Random;

public class PruebaPoligono {
    
    public static void main (String[] args) {
        int numVertices = 10;
        Random rand = new Random();
        PoligonoIrreg poligono = new PoligonoIrreg(numVertices); 

        for(int i = 0 ; i < numVertices; i++){
        	int x = rand.nextInt(15 + 1) + 1;
        	int y = rand.nextInt(15 + 1) + 1;

        	System.out.println(i + " - x: " + x + " y: " + y);
        	poligono.anadeVertice(new Coordenada(x,y));
        }

        System.out.println("Coordenadas del poligono irregular:");
        System.out.println(poligono.toString());
    }
}
