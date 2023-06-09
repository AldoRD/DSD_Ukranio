// 20 - Leer una serie de 10 números, moverlos una posición hacia adelante en el arreglo y mostrar el 
// arreglo resultante. Ejemplo, tenemos el siguiente arreglo 1, 2, 3, 4, 5, si desplazamos los elementos una 
// posición hacia adelante debemos obtener 5, 1, 2, 3, 4. Es decir, el primer elemento se mueve hacia el 
// segundo lugar, el segundo al tercero, etc., y el último al primero.

import java.util.Scanner;

public class Ejercicio20{
  public static void main(String[] args) {
    int[] numeros = new int[10];
    Scanner input = new Scanner(System.in);

    System.out.println("Introduce 10 números:");

    for (int i = 0; i < 10; i++)
      numeros[i] = input.nextInt();

    int ultimoNumero = numeros[9];

    for (int i = 9; i > 0; i--)
      numeros[i] = numeros[i-1];

    numeros[0] = ultimoNumero;

    System.out.println("Arreglo final:");

    for (int i = 0; i < 10; i++)
      System.out.print(numeros[i] + " ");

    input.close();
  }
}
