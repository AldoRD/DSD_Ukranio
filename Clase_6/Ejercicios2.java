import java.text.DecimalFormat;

public class Ejercicios2{
    public static void main(String[] args) {
        DecimalFormat formato = new DecimalFormat("#.0000000000");
            System.out.println(formato.format(Math.PI));
    }
}
