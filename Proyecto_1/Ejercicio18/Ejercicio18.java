// 18 - Leer un arreglo de 10 nombres de ciudades e indicar cuál es la que tiene el nombre más largo.

import java.util.Scanner;

public class Ejercicio18{
  public static void main(String[] args) {
    String[] ciudades = new String[10];
    Scanner input = new Scanner(System.in);

    System.out.println("Introduce los nombres de 10 ciudades:");

    for (int i = 0; i < 10; i++)
      ciudades[i] = input.nextLine();

    String ciudadMasLarga = ciudades[0];

    for (int i = 1; i < 10; i++)
      if (ciudades[i].length() > ciudadMasLarga.length())
        ciudadMasLarga = ciudades[i];

    System.out.println("La ciudad con el nombre más largo es " + ciudadMasLarga);
    input.close();
  }
}


