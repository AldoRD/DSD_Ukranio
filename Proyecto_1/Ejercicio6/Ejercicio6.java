// 6- Calcular el cobro que una caseta de cuota debe realizar a un vehículo de acuerdo con las 
// siguientes reglas: Motocicleta = $20, 2 ejes(automóviles) = $40, 3 ejes (camionetas) = $60, 4, 5 y 6 ejes 
// (camiones de carga) = $250, eje adicional $50. Por ejemplo si llega un trailer de 8 ejes se deben cobrar 
// $350.

import java.util.Scanner;

public class Ejercicio6{
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        int numeroEjes;
        double precio;

        System.out.print("Ingrese la cantidad de ejes del vehículo: ");
        numeroEjes = input.nextInt();

        if (numeroEjes == 1)
            precio = 20.0;
        else if (numeroEjes == 2)
            precio = 40.0;
        else if (numeroEjes == 3)
            precio = 60.0;
        else if (numeroEjes >= 4 && numeroEjes <= 6)
            precio = 250.0;
        else
            precio = 250.0 + ((numeroEjes - 6) * 50.0);

        System.out.println("El precio es: $" + precio);
        input.close();
    }
}
