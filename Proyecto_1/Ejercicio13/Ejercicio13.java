// 13 - Calcular cuanto dinero tendría en una cuenta de ahorro al final de 20 años si al inicio de cada 
// año añado $10,000, el rendimiento anual es 5% y reinvierto las ganancias obtenidas cada año.

public class Ejercicio13 {
    public static void main(String[] args) {
        double balance = 0;
        double interes = 0.05;
        double depositoAño = 10000;
        
        for (int year = 1; year <= 20; year++) {
            balance += depositoAño;
            double interest = balance * interes;
            balance += interest;
        }
        
        System.out.println("El saldo de la cuenta es: $" + balance);
    }
}