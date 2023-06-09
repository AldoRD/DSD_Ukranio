import networking.WebClient;

import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;

public class Aggregator {
    private WebClient webClient;

    public Aggregator() {
        this.webClient = new WebClient();
    }

    public List<String> sendTasksToWorkers(List<String> workersAddresses, List<String> tasks) {
        int tamWorkers = workersAddresses.size();
        int tamTasks = tasks.size();
        CompletableFuture<String>[] futures = new CompletableFuture[tamTasks];
        StringBuilder impresion = new StringBuilder("");

        for (int i = 0; i < tamWorkers; i++) {
            String workerAddress = workersAddresses.get(i);
            String task = tasks.get(i);

            byte[] requestPayload = task.getBytes();
            futures[i] = webClient.sendTask(workerAddress, requestPayload);
            impresion.append("\tServidor: " + workerAddress + " -> Tarea: " + task + "\n");
        }

        System.out.println(impresion);

        List<String> results = new ArrayList();
        for (int i = 0; i < tamTasks; i++) {
            results.add(futures[i].join());
        }

        return results;
    }
}