import java.util.ArrayList;
import java.util.Iterator;

public class Ordenados {
    public static void main(String[] args) {
        ArrayList<String> curps = new ArrayList<String>();
        int n = Integer.parseInt(args[0]);

        System.out.println("\nCURP Originales");
        for (int i = 0; i < n; i++) {
            curps.add(getCURP());
            System.out.println("CURP = " + curps.get(i));
        }
        generarEnOrdenAlfabetico(curps, n);
    }

    public static void generarEnOrdenAlfabetico(ArrayList<String> curps, int numCurps) {
        ArrayList<String> curpsOrdenados = new ArrayList<String>();
        Iterator<String> itr = curps.iterator();

        while (itr.hasNext()) {
            String c1 = itr.next();
            if (curpsOrdenados.size() == 0) {
                curpsOrdenados.add(c1);
            } else {
                for (int i = 0; i < curpsOrdenados.size(); i++) {
                    int compare = c1.substring(0, 4).compareTo(curpsOrdenados.get(i).substring(0, 4));
                    if (compare > 0 && i < curpsOrdenados.size() - 1) {
                        continue;
                    } else if (compare <= 0) {
                        curpsOrdenados.add(i, c1);
                        break;
                    } else {
                        curpsOrdenados.add(i + 1, c1);
                        break;
                    }
                }
            }
        }

        System.out.println("\nCURP Ordenados");
        for (int i = 0; i < curpsOrdenados.size(); i++) {
            System.out.println("CURP = " + curpsOrdenados.get(i));
        }
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
