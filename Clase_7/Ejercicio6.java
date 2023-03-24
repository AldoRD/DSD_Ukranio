import java.util.concurrent.ThreadLocalRandom;

public class Ejercicio6 {
    public static void main(String[] args){
        long init = System.nanoTime();
        int n = Integer.parseInt(args[0]);
        StringBuilder cadenaLarga = new StringBuilder();

        for(int i = 0; i < n; i++)
            cadenaLarga.append(generadorPalabras());
        
        System.out.println(buscarIPN(cadenaLarga));
        long end = System.nanoTime();
        System.out.print("time in nanoseconds = ");
        System.out.println(end - init);
    }
    
    public static String generadorPalabras(){
        int longitud = 3;
        String banco = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String cadena = "";

        for (int x = 0; x < longitud; x++) {
            int indiceAleatorio = numeroAleatorioEnRango(0, banco.length() - 1);
            char caracterAleatorio = banco.charAt(indiceAleatorio);
            cadena += caracterAleatorio;
        }
        cadena += " ";
        return cadena;
    }

    public static int numeroAleatorioEnRango(int minimo, int maximo) {
        return ThreadLocalRandom.current().nextInt(minimo, maximo + 1);
    }

    public static int buscarIPN (StringBuilder cadena) {
        int count = 0;
        int lastIndex = 0;

        while(lastIndex >= 0){
             lastIndex =  cadena.indexOf("IPN ", lastIndex);
             if(lastIndex >= 0){
                count += 1;
                lastIndex += 4;
             }
        }

        return count;
    }
}
