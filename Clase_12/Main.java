import java.util.ArrayList;
import java.util.Iterator;

public class Main {
    public static void main(String[] args) {
        ArrayList<String> curps = new ArrayList<String>();
        int n = Integer.parseInt(args[0]);
        String sexoFilterValue = String.valueOf(args[1]);

        System.out.println("CURP Orginales");
        for (int i = 0; i < n; i++) {
            curps.add(getCURP());
            System.out.println("CURP = " + curps.get(i));
        }

        eliminarCurpsPorSexo(curps, sexoFilterValue);
    }

    public static void eliminarCurpsPorSexo(ArrayList<String> curps, String sexo) {
        Iterator<String> itr = curps.iterator();
        while (itr.hasNext()) {
            String curp = itr.next();
            if (curp.charAt(10) == sexo.charAt(0))
                itr.remove();
        }

        System.out.println("\nCURP Filtrados");
        for (int i = 0; i < curps.size(); i++) {
            System.out.println("CURP = " + curps.get(i));
        }
    }

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
