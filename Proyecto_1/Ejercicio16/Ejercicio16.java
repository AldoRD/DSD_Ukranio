// 16 - El programa debe leer números mientras sean diferentes de 0. Posteriormente calcular el 
// promedio de los números leídos.

import java.util.Scanner;

public class Ejercicio16{
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        int suma = 0;
        int contador = 0;

        System.out.println("Ingresa una serie de números (0 para salir):");

        int numero = input.nextInt();

        while (numero != 0) {
            suma += numero;
            contador += 1;
            numero = input.nextInt();
        }

        if (contador == 0)
            System.out.println("No hay numeros");
        else {
            double promedio = (double) suma / contador;
            System.out.println("El promedio: " + promedio);
        }

        input.close();
    }
}
