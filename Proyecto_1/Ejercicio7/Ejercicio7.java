// 7- La Comisión Federal de Electricidad cobra el consumo de electricidad de acuerdo con un 
// tabulador basado en los kilowatts consumidos en el periodo. 

import java.util.Scanner;

public class Ejercicio7{
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.print("Ingrese la cantidad de kW consumidos: ");
        int kWConsumidos = input.nextInt();

        System.out.print("Ingrese el tipo de contrato H o N: ");
        String contrato = input.next();
        input.close();

        double costoTotal = 0;

        if (contrato.equalsIgnoreCase("H")) {
            if (kWConsumidos <= 250)
                costoTotal = kWConsumidos * 0.65;
            else if (kWConsumidos <= 500)
                costoTotal = 250 * 0.65 + (kWConsumidos - 250) * 0.85;
            else if (kWConsumidos <= 1200)
                costoTotal = 250 * 0.65 + 250 * 0.85 + (kWConsumidos - 500) * 1.5;
            else if (kWConsumidos <= 2100)
                costoTotal = 250 * 0.65 + 250 * 0.85 + 700 * 1.5 + (kWConsumidos - 1200) * 2.5;
            else
                costoTotal = 250 * 0.65 + 250 * 0.85 + 700 * 1.5 + 900 * 2.5 + (kWConsumidos - 2100) * 3.0;
        } else if (contrato.equalsIgnoreCase("N"))
            costoTotal = kWConsumidos * 5.0;
        else {
            System.out.println("El dato es invalido");
            return;
        }

        System.out.println("El costo total es de: $" + costoTotal);
    }
}

