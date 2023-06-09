// 17 - Leer 10 números enteros, guardarlos en orden inverso al que fueron introducidos y mostrarlos 
// en pantalla

import java.util.Scanner;

public class Ejercicio17{
  public static void main(String[] args) {
    int[] numeros = new int[10];
    Scanner input = new Scanner(System.in);

    System.out.println("Introduce 10 números: ");

    for (int i = 0; i < 10; i++)
      numeros[i] = input.nextInt();

    System.out.println("Los números en inverso:");

    for (int i = 9; i >= 0; i--)
      System.out.println(numeros[i]);

    input.close();
  }
}
