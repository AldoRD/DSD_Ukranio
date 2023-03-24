import java.util.concurrent.ThreadLocalRandom;

public class Ejercicio5 {
    public static void main(String[] args){
        long init = System.nanoTime();
        int n = Integer.parseInt(args[0]);
        char[] cadenaLarga = new char[n*4];
        String temp = "";
        
        for(int i = 0; i < n; i++)
            temp += generadorPalabras();
        
        cadenaLarga = temp.toCharArray();
        
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

    public static int buscarIPN (char[] array) {
        int count = 0;
        String lastWord = "";

        for(int i = 0; i < array.length; i++){
            lastWord += array[i];
            if(lastWord.length() == 4){
                if(lastWord.equals("IPN "))
                    count += 1;
                lastWord = "";
            }
        }

        return count;
    }
}