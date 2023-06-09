// 22 - Escribe un programa que solicite al usuario los tamaños de las dos matrices a multiplicar y luego 
// solicite los valores, realice la multiplicación y muestre el resultado

import java.util.Scanner;

public class Ejercicio22{
  public static void main(String[] args) {
    Scanner input = new Scanner(System.in);
    System.out.println("Introduce el tamaño de la matriz 1 (filas columnas):");
    int row1 = input.nextInt();
    int cols1 = input.nextInt();

    System.out.println("Introduce el tamaño de la matriz 2 (filas columnas):");
    int row2 = input.nextInt();
    int cols2 = input.nextInt();

    if (cols1 != row2) {
      System.out.println("No es posible realizar la multiplicación");
      input.close();
      return;
    }

    int[][] matrizA = new int[row1][cols1];
    int[][] matrizB = new int[row2][cols2];
    int[][] res = new int[row1][cols2];

    System.out.println("Introduce los elementos de la matriz A:");

    for (int i = 0; i < row1; i++) {
      for (int j = 0; j < cols1; j++) {
        matrizA[i][j] = input.nextInt();
      }
    }

    System.out.println("Introduce los elementos de la matriz B:");

    for (int i = 0; i < row2; i++)
      for (int j = 0; j < cols2; j++)
        matrizB[i][j] = input.nextInt();
    

    for (int i = 0; i < row1; i++) 
      for (int j = 0; j < cols2; j++)
        for (int k = 0; k < cols1; k++)
          res[i][j] += matrizA[i][k] * matrizB[k][j];
        
    System.out.println("El resultado es:");

    for (int i = 0; i < row1; i++) {
      for (int j = 0; j < cols2; j++)
        System.out.print(res[i][j] + " ");
      System.out.println();
    }

    input.close();
  }
}
