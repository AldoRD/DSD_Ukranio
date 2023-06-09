// 3- Escribe un programa que calcule el radio de la circunferencia inscrita en un triángulo

import java.util.Scanner;

public class Ejercicio3{

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.println("Ingresa las medidas de los lados de un triángulo: ");
        System.out.print("Lado a: ");
        double a = input.nextDouble();
        System.out.print("Lado b: ");
        double b = input.nextDouble();
        System.out.print("Lado c: ");
        double c = input.nextDouble();

        double s = (a + b + c) / 2;
        double area = Math.sqrt(s * (s - a) * (s - b) * (s - c));
        double radio = area / s;

        System.out.println("El radio de la circunferencia es: " + radio);
        input.close();
    }
}
