// 19 - Leer en un arreglo una serie de 10 números e indicar si todos los elementos están ordenados de 
// forma descendente, es decir, si cumplen la regla de que cada elemento del arreglo es menor o igual que 
// el elemento anterior.

import java.util.Scanner;

public class Ejercicio19{
  public static void main(String[] args) {
    int[] numeros = new int[10];
    Scanner input = new Scanner(System.in);

    System.out.println("Introduce 10 números:");

    for (int i = 0; i < 10; i++)
      numeros[i] = input.nextInt();

    boolean ordenDescendente = true;

    for (int i = 1; i < 10; i++)
      if (numeros[i] > numeros[i-1]) {
        ordenDescendente = false;
        break;
      }

    if (ordenDescendente)
      System.out.println("Los números están ordenados");
    else
      System.out.println("Los números no están ordenados");
    
    input.close();
  }
}

