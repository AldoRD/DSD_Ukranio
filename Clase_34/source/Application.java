import java.util.*;
import java.io.*;

public class Application {
    // ----------|Variables Globales|----------
    private static final String WORKER_ADDRESS_1 = "http://localhost:8081/task";

    // ====================|Metodo Principal|====================
    public static void main(String[] args) {
        // ----------|Variables Locales|----------
        Random rand = new Random();
        Aggregator aggregator = new Aggregator();
        PoligonoIrreg pol = new PoligonoIrreg();
        int vertices = 3;

        // ----------|Serializacion|----------
        for (int i = 0; i < vertices; i++) {
            int x = rand.nextInt(15 + 1) + 1;
            int y = rand.nextInt(15 + 1) + 1;

            pol.anadeVertice(new Coordenada(x, y));
        }
        while (true) {
            System.out.println("\t Antes de serialize: ");
            System.out.println(pol.toString());
            byte[] serializado = SerializationUtils.serialize(pol);
            System.out.println("Serializado: " + serializado);

            // ----------|Envio de servidores y solicitudes|----------
            List<byte[]> results = aggregator.sendTasksToWorkers(Arrays.asList(WORKER_ADDRESS_1),
                    Arrays.asList(serializado));

            // ----------|Impresion de resultados|----------
            PoligonoIrreg objeto = null;
            for (byte[] result : results) {
                objeto = (PoligonoIrreg) SerializationUtils.deserialize(result);
                System.out.println(objeto.toString());
            }
            pol = objeto;
            try {
                Thread.sleep(1000);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}