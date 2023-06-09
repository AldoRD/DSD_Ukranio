// 2- Escriba un programa que solicite los valores de a, b, c y d (como números enteros) y calcule el valor de la 
// multiplicación y lo muestre en pantalla en forma de número con decimales y en forma de fracción 
// (ejemplo 17/33).

import java.util.Scanner;

public class Ejercicio2 {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.print("Ingrese a: ");
        int a = input.nextInt();
        System.out.print("Ingrese b: ");
        int b = input.nextInt();
        System.out.print("Ingrese c: ");
        int c = input.nextInt();
        System.out.print("Ingrese d: ");
        int d = input.nextInt();
        
        int numerador = a * c;
        int denominador = b * d;
        
        double resultadoDecimal = (double) numerador / denominador;
        System.out.println("El resultado decimal es: " + resultadoDecimal);
        System.out.println("El resultado en fracción es: " + numerador + "/" + denominador);
        input.close();
    }
}
