// 1- Escribir un programa que reciba una cantidad en grados centígrados e indique a cuánto 
// equivalen en grados Fahrenheit.

import java.util.Scanner;

public class Ejercicio1 {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        System.out.print("Temperatura en grados Celsius: ");

        double celsius = input.nextDouble();
        double fahrenheit = (celsius * 1.8) + 32;

        System.out.println(celsius + " grados Celsius equivale a " + fahrenheit + " grados Fahrenheit.");
        input.close();
    }
}
