// Encontrar los números entre el 1 y el 5000 que cumplen la regla de que la suma del cubo de sus 
// dígitos es igual al número mismo. Ejemplo: 153 = 1^3 + 5^3 + 3^3

public class Ejercicio11{
    public static void main(String[] args) {
        for (int i = 1; i <= 5000; i++) {
            int numero = i;
            int suma = 0;
            
            while (numero > 0) {
                int digito = numero % 10;
                suma += Math.pow(digito, 3);
                numero /= 10;
            }
            
            if (suma == i)
                System.out.println(i + " cumple con la regla");
        }
    }
}

