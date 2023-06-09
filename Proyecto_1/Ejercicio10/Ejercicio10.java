// 10- Escribe un programa que solicite al usuario un número entero y dé como resultado la suma de 
// todos los números desde el 1 hasta dicho numero

import java.util.Scanner;

public class Ejercicio10{
   public static void main(String[] args) {
      Scanner input = new Scanner(System.in);
      System.out.print("Introduce un número positivo: ");
      int numero = input.nextInt();
      
      int sumatotal = 0;
      for (int num = 1; num <= numero; num++)
         sumatotal += num;
      
      System.out.println("La suma de los números desde 1 hasta " + numero + " es: " + sumatotal);
      input.close();
   }
}

