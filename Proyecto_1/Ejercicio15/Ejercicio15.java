// 15 - En este programa la computadora debe elegir un número al azar entre 1 y 100 y solicitará al 
// usuario que “adivine” el número. A cada intento del usuario la computadora debe indicar si el número 
// que el usuario introdujo es mayor o menor que el número prefijado. El programa debe terminar cuando 
// el usuario “adivine” el número o introduzca el número “0” para salir.

import java.util.Random;
import java.util.Scanner;

public class Ejercicio15{

    public static void main(String[] args) {
        Random rand = new Random();
        int numeroRandom = rand.nextInt(100) + 1;
        Scanner input = new Scanner(System.in);
        int intentos = 0;
        int numeroIngresado;
        
        System.out.println("Ingresa un número entre 1 y 100 para adivinar el número (0 para salir)");
        
        while (true) {
            intentos++;
            System.out.print("Intento #" + intentos + ": ");
            numeroIngresado = input.nextInt();
            
            if (numeroIngresado == 0) {
                System.out.println("Hasta luego");
                break;
            } else if (numeroIngresado == numeroRandom) {
                System.out.println("Adivinaste el número en " + intentos + " intentos.");
                break;
            } else if (numeroIngresado > numeroRandom)
                System.out.println("El número es menor.");
            else
                System.out.println("El número es mayor.");
        }
        input.close();
    }
}

