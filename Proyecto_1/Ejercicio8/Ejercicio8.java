// 8- Escribe un programa que pida tres números y que los muestre ordenados de mayor a menor.

import java.util.Scanner;

public class Ejercicio8{
   public static void main(String[] args) {

      Scanner input = new Scanner(System.in);
      System.out.print("Primer número: ");
      int num1 = input.nextInt();
      System.out.print("Segundo número: ");
      int num2 = input.nextInt();
      System.out.print("Tercer número: ");
      int num3 = input.nextInt();

      if (num1 >= num2 && num1 >= num3) {
         if (num2 >= num3)
            System.out.println(num1 + ", " + num2 + ", " + num3);
         else
            System.out.println(num1 + ", " + num3 + ", " + num2);
      } else if (num2 >= num1 && num2 >= num3) {
         if (num1 >= num3)
            System.out.println(num2 + ", " + num1 + ", " + num3);
         else
            System.out.println(num2 + ", " + num3 + ", " + num1);
      } else if (num3 >= num1 && num3 >= num2) {
         if (num1 >= num2)
            System.out.println(num3 + ", " + num1 + ", " + num2);
         else
            System.out.println(num3 + ", " + num2 + ", " + num1);
      }
      input.close();
   }
}

