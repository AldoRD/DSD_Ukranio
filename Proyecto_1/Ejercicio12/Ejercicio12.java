// 12 - Un número perfecto es un número natural que es igual a la suma de sus divisores propios, sin 
// incluirse él mismo. Por ejemplo, el número 6 es perfecto porque sus divisores son 1, 2 y 3; y dado que 
// 1+2+3 = 6 entonces el 6 es un número perfecto. Escriba un programa que indique los números perfectos 
// existentes entre 1 y 10,000

public class Ejercicio12{
    public static void main(String[] args) {
        int n = 10000;
        for (int i = 1; i <= n; i++) {
            int sum = 0;
            for (int j = 1; j < i; j++)
                if (i % j == 0)
                    sum += j;
            if (sum == i)
                System.out.println(i + " :número perfecto");
        }
    }
}

