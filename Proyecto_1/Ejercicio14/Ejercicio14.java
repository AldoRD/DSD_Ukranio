// Escribe un programa que lea un número X y un número Y. Mostrar los números de Y en Y, 
// comenzando en X hasta llegar a 200. Ejmplo: X = 8, Y = 2, el resultado comenzaría de la siguiente 
// manera: 8, 10, 12, 14, 16, 18 …

import java.util.Scanner;

public class Ejercicio14{
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        System.out.print("Ingrese X: ");
        int x = input.nextInt();
        System.out.print("Ingrese Y: ");
        int y = input.nextInt();

        int num = x;

        while (num <= 200) {
            System.out.print(num + " ");
            num += y;
        }
        input.close();
    }
}
