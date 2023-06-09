
//Proyecto 3
//Ramirez Dominguez Aldo Eduardo
//4CM14
import java.io.*;
import java.util.*;

public class GenerarEstadisticas {
    /**
     * La función lee un archivo que contiene CURPs, extrae el partido político de
     * cada persona, y actualizaciones
     * un mapa con el número de votos de cada partido, generando periódicamente una
     * barra
     * gráfico de los resultados.
     */
    public static void main(String args[]) {
        String archivo = "curps.txt";
        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {

            Map<String, Integer> votos = new HashMap<>();
            long utlimoRefresh = System.currentTimeMillis();

            while (true) {
                String linea;
                while ((linea = br.readLine()) != null) {
                    String partido = getPartido(linea);
                    votos.put(partido, votos.getOrDefault(partido, 0) + 1);
                }

                if (System.currentTimeMillis() - utlimoRefresh > 3000) {
                    generarBarras(votos);
                    utlimoRefresh = System.currentTimeMillis();
                }
                Thread.sleep(3000);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * La función genera un gráfico de barras basado en el número de votos para cada
     * partido político en un
     * elección dada.
     */
    public static void generarBarras(Map<String, Integer> votos) {
        int votosTotales = 0;
        LinkedList<Integer> totalVotosPorPartido = new LinkedList<>();

        for (Map.Entry<String, Integer> e : votos.entrySet()) {
            System.out.println(" - " + e.getKey() + " " + e.getValue());
            totalVotosPorPartido.add(Integer.parseInt(e.getKey()), e.getValue());
            votosTotales += e.getValue();
        }

        int anchuraBarra = 50;
        int[] barrasDeProgreso = new int[votos.size()];
        float[] porcentajesPorPartido = new float[votos.size()];
        for (int i = 0; i < totalVotosPorPartido.size(); i++) {
            float porcentaje = totalVotosPorPartido.get(i) * 100.0f / votosTotales;
            porcentajesPorPartido[i] = porcentaje;
            barrasDeProgreso[i] = (int) (anchuraBarra * (porcentaje / 100.0f));
        }

        // Dibujar la barra de progreso en la consola, borrando la pantalla primero
        System.out.print("\033[H\033[2J");
        System.out.flush();
        System.out.println("Numero Total de votos: " + votosTotales);
        for (int i = 0; i < totalVotosPorPartido.size(); i++) {
            System.out.print("Partido " + i + ":\t[");
            for (int j = 0; j < barrasDeProgreso[i]; j++) {
                System.out.print("=");
            }
            System.out.print(">");
            for (int j = barrasDeProgreso[i] + 1; j < anchuraBarra; j++) {
                System.out.print(" ");
            }
            System.out.print("] " + totalVotosPorPartido.get(i) + " votos  %" + porcentajesPorPartido[i] + "\n");
        }
    }

    /**
     * La función devuelve el último carácter de una cadena dada.
     */
    public static String getPartido(String curp) {
        return curp.substring(curp.length() - 1);
    }
}
