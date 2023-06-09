import networking.WebClient;

import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;

public class Aggregator {
    private WebClient webClient;

    public Aggregator() {
        this.webClient = new WebClient();
    }

    public List<byte[]> sendTasksToWorkers(List<String> workersAddresses, List<byte[]> tasks) {
        // ----------|Variables Locales|----------
        int tamWorkers = workersAddresses.size();
        CompletableFuture<byte[]>[] futures = new CompletableFuture[tamWorkers];

        // ----------|Envio de primera(s) solicitud(es)|----------
        for (int i = 0; i < tamWorkers; i++) {
            String workerAddress = workersAddresses.get(i);
            byte[] requestPayload = tasks.get(i);

            futures[i] = webClient.sendTask(workerAddress, requestPayload);
        }

        // ----------|Evaluacion de Termino|----------
        boolean bandera = true;
        while (bandera) {
            for (int j = 0; j < tamWorkers; j++) {
                if (true == futures[j].isDone())
                    bandera = false;
            }
        }

        // ----------|Resultados futures[]|----------
        List<byte[]> results = new ArrayList();
        for (int i = 0; i < tasks.size(); i++) {
            results.add(futures[i].join());
        }

        return results;
    }
}