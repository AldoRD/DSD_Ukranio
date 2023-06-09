// Proyecto 4
// Ramirez Dominguez Aldo Eduardo
// 4CM14

import networking.WebClient;

import java.util.List;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.concurrent.CompletableFuture;

import javax.xml.catalog.CatalogFeatures.Feature;

public class Aggregator {
    private WebClient webClient;

    public Aggregator() {
        this.webClient = new WebClient();
    }

    public List<String> sendTasksToWorkers(List<String> workersAddresses, List<String> tareas) {
        int index_tarea = 0;
        int num_workers = workersAddresses.size();
        int num_tareas = tareas.size();
        List<String> faltantes = new ArrayList<String>();
        CompletableFuture<String>[] futures = new CompletableFuture[num_tareas];

        // Este bloque de código envía tareas a los trabajadores. Está iterando a través
        // de la lista.
        // de trabajador
        // direcciones y tareas correspondientes, creando una carga útil de matriz de
        // bytes para cada
        // tarea y envío
        // al trabajador usando el método `webClient.sendTask()`. También imprime un
        // mensaje
        // indicando la dirección del trabajador y la tarea que se envió. El
        // `index_tarea`
        // la variable se usa para
        // realiza un seguimiento de la tarea actual que se envía.
        for (int i = 0; i < num_workers; i++) {
            String workerAddress = workersAddresses.get(i);
            String tarea = tareas.get(i);
            byte[] requestPayload = tarea.getBytes();
            futures[i] = webClient.sendTask(workerAddress, requestPayload);
            System.out.println("\tEnviado a la dirección: " + workerAddress + " La Tarea: " + tarea + "\n");
            index_tarea = index_tarea + 1;
        }

        // Este bloque de código verifica si alguna de las tareas enviadas a los
        // trabajadores ha sido
        // completado. Él
        // hace esto iterando a través de la lista de `futuros` (que son
        // Objetos `Futuro Completable`
        // representando las tareas enviadas a cada trabajador) y comprobando si alguno
        // de ellos tiene
        // completado usando
        // el método `isDone()`. Si una tarea se ha completado, envía la siguiente tarea
        // en el
        // lista a la
        // mismo trabajador usando el método `webClient.sendTask()`, e incrementa el
        // `index_tareas`
        // variable para realizar un seguimiento de la siguiente tarea que se enviará.
        // Este bucle continúa hasta que
        // todas las tareas tienen
        // ha sido enviado y completado.
        while (index_tarea < num_tareas) {
            for (int i = 0; i < num_workers; i++) {
                if (futures[i].isDone()) {
                    String workerAddress = workersAddresses.get(i);
                    String tarea = tareas.get(index_tarea);
                    byte[] requestPayload = tarea.getBytes();

                    System.out.println("\t" + futures[i].join() + "\n");
                    futures[i] = webClient.sendTask(workerAddress, requestPayload);
                    System.out.println("\tEnviado a la dirección: " + workerAddress + " con la Tarea: " + tarea + "\n");
                    index_tarea += 1;
                }
            }
        }

        // Este bloque de código itera a través de la lista de objetos
        // `CompletableFuture`
        // (`futuros`)
        // representando las tareas enviadas a cada trabajador y llamando al método
        // `join()`
        // en cada uno. El
        // El método `join()` bloquea hasta la tarea representada por
        // `CompletableFuture`
        // esta completado,
        // y devuelve el resultado de la tarea. Luego se suma el resultado al
        // lista de `faltantes`, que
        // contendrá los resultados de todas las tareas que no se completaron con éxito.
        // Finalmente, el
        // La lista `faltantes` es devuelta por el método `sendTasksToWorkers()`.
        for (int i = 0; i < num_workers; i++)
            faltantes.add(futures[i].join());

        return faltantes;
    }
}