// 21 - Escribe un programa que lea un arreglo bidimensional de 5x5 y muestre la suma del total del 
// arreglo.

import java.util.Scanner;

public class Ejercicio21{
  public static void main(String[] args) {
    int[][] arreglo = new int[5][5];
    int suma = 0;
    Scanner input = new Scanner(System.in);

    System.out.println("Introduce los elementos del arreglo:");

    for (int i = 0; i < 5; i++)
      for (int j = 0; j < 5; j++) {
        arreglo[i][j] = input.nextInt();
        suma += arreglo[i][j];
      }

    System.out.println("La suma total es: " + suma);
    input.close();
  }
}

