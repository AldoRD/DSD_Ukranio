/*
 *Licencia MIT
 *
 *Copyright (c) 2019 Michael Pogrebinsky -Sistemas distribuidos y computación en la nube con Java
 *
 *Por la presente se concede permiso, sin cargo, a cualquier persona que obtenga una copia
 *de este software y los archivos de documentación asociados (el "Software"), para tratar
 *en el Software sin restricción, incluidos, entre otros, los derechos
 *para usar, copiar, modificar, fusionar, publicar, distribuir, sublicenciar y/o vender
*copias del Software, y para permitir a las personas a quienes se les
 *equipado para hacerlo, sujeto a las siguientes condiciones:
 *
 *El aviso de derechos de autor anterior y este aviso de permiso se incluirán en todos
 *copias o partes sustanciales del Software.
 *
 *EL SOFTWARE SE PROPORCIONA "TAL CUAL", SIN GARANTÍA DE NINGÚN TIPO, EXPRESA O
 *IMPLÍCITO, INCLUYENDO PERO NO LIMITADO A LAS GARANTÍAS DE COMERCIABILIDAD,
*IDONEIDAD PARA UN FIN DETERMINADO Y NO VIOLACIÓN. EN NINGÚN CASO LA
 *LOS AUTORES O TITULARES DE LOS DERECHOS DE AUTOR SERÁN RESPONSABLES DE CUALQUIER RECLAMACIÓN, DAÑOS U OTROS
 *RESPONSABILIDAD, YA SEA EN UNA ACCIÓN DE CONTRATO, AGRAVIO O DE OTRA FORMA, DERIVADA DE,
 *FUERA DE O EN CONEXIÓN CON EL SOFTWARE O EL USO U OTROS TRATOS EN EL
 * SOFTWARE.
 */

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

        /*
         * Se envian las primeras dos tareas a los servidores
         */
        for (int i = 0; i < tamWorkers; i++) {
            // Este bloque de código envía tareas a los trabajadores. Está iterando a través
            // de la lista de trabajadores.
            // direcciones y tareas, obteniendo la dirección y la tarea del trabajador en el
            // índice actual, convirtiendo
            // la tarea a una matriz de bytes, y luego enviar la tarea al trabajador usando
            // el
            // Método `webClient.sendTask()`. El resultado de la tarea se almacena en un
            // `CompletableFuture`
            // objeto en el mismo índice que la tarea en la matriz `futures`. La `impresión`
            // StringBuilder se usa para imprimir la dirección del servidor y la tarea que
            // se le envió.
            String workerAddress = workersAddresses.get(i);
            String task = tasks.get(i);

            byte[] requestPayload = task.getBytes();
            futures[i] = webClient.sendTask(workerAddress, requestPayload);
            impresion.append("\tServidor: " + workerAddress + " -> Tarea: " + task + "\n");
        }

        /*
         * Evalúa continuamente si uno de los servidores ha terminado para enviar la
         * siguiente
         * tarea.
         */
        boolean bandera = true;
        while (bandera) {
            for (int j = 0; j < tamWorkers; j++) {
                // La sentencia `if` está comprobando si `CompletableFuture` en el índice `j` en
                // el
                // La matriz `futures` está hecha o completa. El método `isDone()` devuelve un
                // valor booleano
                // indicando si el cómputo asociado con `CompletableFuture` ha
                // completado normalmente o excepcionalmente. La parte `true ==` es redundante y
                // puede ser
                // simplificado a solo `si (futuros[j].isDone())`.
                if (true == futures[j].isDone()) {
                    String workerAddress = workersAddresses.get(j);
                    String task = tasks.get(2);

                    byte[] requestPayload = task.getBytes();
                    futures[2] = webClient.sendTask(workerAddress, requestPayload);
                    impresion.append("\n Servidor Completado: " + workerAddress + ". Se le asigna -> Tarea: "
                            + task + "\n");
                    bandera = false;
                }
            }
        }

        System.out.println(impresion);

        List<String> results = new ArrayList();
        for (int i = 0; i < tamTasks; i++) {
            results.add(futures[i].join());
        }

        return results;
    }
}