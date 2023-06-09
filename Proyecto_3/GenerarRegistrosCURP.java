
//Proyecto 3
//Ramirez Dominguez Aldo Eduardo
//4CM14
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.Random;

public class GenerarRegistrosCURP {

    public static void main(String[] args) {
        int velocidad = Integer.parseInt(args[0]);

        try (PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("curps.txt")))) {
            while (true) {
                String curp = getCURP() + "-" + getPartido(0, 3);
                out.println(curp);
                out.flush();
                Thread.sleep(1000 / velocidad);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * La función genera un número entero aleatorio entre m y n, usando la corriente
     * el tiempo como semilla.
     */
    public static String getPartido(int m, int n) {
        Calendar cal = Calendar.getInstance();
        long semilla = cal.getTimeInMillis();
        Random random = new Random(semilla);
        int numeroAleatorio = random.nextInt(n - m + 1) + m;
        return String.valueOf(numeroAleatorio);
    }

    /**
     * La función genera un CURP aleatorio (código único de identificación para
     * personas físicas en México) con
     * una combinación de letras y números.
     */
    public static String getCURP() {

        String Letra = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String Numero = "0123456789";
        String Sexo = "HM";

        String Entidad[] = { "AS", "BC", "BS", "CC", "CS", "CH", "CL", "CM", "DF", "DG", "GT", "GR", "HG", "JC", "MC",
                "MN", "MS", "NT", "NL", "OC", "PL", "QT", "QR", "SP", "SL", "SR", "TC", "TL", "TS", "VZ", "YN", "ZS" };

        int indice;
        StringBuilder sb = new StringBuilder(18);

        for (int i = 1; i < 5; i++) {
            indice = (int) (Letra.length() * Math.random());
            sb.append(Letra.charAt(indice));
        }

        for (int i = 5; i < 11; i++) {
            indice = (int) (Numero.length() * Math.random());
            sb.append(Numero.charAt(indice));
        }

        indice = (int) (Sexo.length() * Math.random());
        sb.append(Sexo.charAt(indice));
        sb.append(Entidad[(int) (Math.random() * 32)]);

        for (int i = 14; i < 17; i++) {
            indice = (int) (Letra.length() * Math.random());
            sb.append(Letra.charAt(indice));
        }

        for (int i = 17; i < 19; i++) {
            indice = (int) (Numero.length() * Math.random());
            sb.append(Numero.charAt(indice));
        }

        return sb.toString();
    }
}
