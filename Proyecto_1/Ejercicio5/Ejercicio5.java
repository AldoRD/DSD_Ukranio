// Escribir un programa que indique cuántos días de vacaciones le corresponden a un empleado 
// considerando los años que ha trabajado en la empresa. Entre 1 y 5 años corresponden 5 días de 
// vacaciones, entre 6 y 10 años deben ser 10 días de vacaciones, de allí en adelante, es un día de 
// vacaciones extra por cada año de trabajo (a partir de 10), a partir de 20 años de trabajo se añaden 2 días 
// de vacaciones por cada año hasta un máximo de 45 días.

import java.util.Scanner;

public class Ejercicio5{
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Ingrese los años trabajados en la empresa: ");
        int tiempoTrabajado = scanner.nextInt();
        scanner.close();
        
        int diasVacaciones;
        if (tiempoTrabajado >= 1 && tiempoTrabajado <= 5)
            diasVacaciones = 5;
        else if (tiempoTrabajado >= 6 && tiempoTrabajado <= 10)
            diasVacaciones = 10;
        else if (tiempoTrabajado >= 11 && tiempoTrabajado <= 19)
            diasVacaciones = 10 + (tiempoTrabajado - 10);
        else if (tiempoTrabajado >= 20) {
            diasVacaciones = 25 + (tiempoTrabajado - 20) * 2;
            if (diasVacaciones > 45) {
                diasVacaciones = 45;
            }
        } else {
            System.out.println("El número no es válido.");
            return;
        }
        
        System.out.println("El empleado tiene" + diasVacaciones + " días de vacaciones.");
    }
}
