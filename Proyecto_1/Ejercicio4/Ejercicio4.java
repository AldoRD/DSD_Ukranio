// 5- Una universidad comenzará a asignar a sus estudiantes a diferentes dormitorios según su sexo y 
// edad. Escribe un programa que solicite el sexo (H/M) y edad e indique de acuerdo con la siguiente tabla 
// en qué edificio deben ser asignados. 

import java.util.Scanner;

public class Ejercicio4{
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        System.out.println("Ingrese el sexo H o M: ");
        String sexo = input.nextLine().toUpperCase();
        System.out.println("Ingrese la edad: ");
        int edad = input.nextInt();
        String edificio = "";

        if ((sexo.equals("H") || sexo.equals("M")) && edad >= 18) {
            if (sexo.equals("H") && edad == 18) 
                edificio = "A";
            else if (sexo.equals("M") && edad == 18) 
                edificio = "B";
            else if (sexo.equals("H") && edad >= 19 && edad <= 22)
                edificio = "C";
            else if (sexo.equals("M") && edad >= 19 && edad <= 22)
                edificio = "D";
            else if (sexo.equals("H") && edad > 22)
                edificio = "E1";
            else if (sexo.equals("M") && edad > 22)
                edificio = "E2";
            System.out.println("El estudiante pertenece al edificio " + edificio);
        } else {
            System.out.println("Valores no validos.");
        }
        input.close();
    }
}
