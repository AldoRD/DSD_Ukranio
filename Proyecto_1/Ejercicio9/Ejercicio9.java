// 9- Lee la definición de números capicúa y escribe un programa que reciba un número entre 0 y 
// 9999 e indique si es un número capicúa. Nota, no es necesario utilizar ciclos, es posible realizarlo 
// utilizando divisiones enteras.

import java.util.Scanner;

public class Ejercicio9{
    public static void main(String[] args) {
        String isCapicua = "El número es capicúa";
        String isNotCapicua = "El número no es capicúa";
        Scanner input = new Scanner(System.in);
        int num, unit, decenas, centenas, millares;

        System.out.print("Ingrese un número (0-9999): ");
        num = input.nextInt();

        if (num < 10) {
            System.out.println(isCapicua);
        } else if (num < 100) {
            if (num / 10 == num % 10)
                System.out.println(isCapicua);
            else
                System.out.println(isNotCapicua);
        } else if (num < 1000) {
            unit = num % 10;
            centenas = num / 100;
            if (unit == centenas)
                System.out.println(isCapicua);
            else
                System.out.println(isNotCapicua);
        } else if (num < 10000) {
            unit = num % 10;
            decenas = (num / 10) % 10;
            centenas = (num / 100) % 10;
            millares = num / 1000;
            if (unit == millares && decenas == centenas)
                System.out.println(isCapicua);
            else
                System.out.println(isNotCapicua);
        } else {
            System.out.println("El número no es válido");
        }
        input.close();
    }
}

