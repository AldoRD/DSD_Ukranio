// Proyecto 4
// Ramirez Dominguez Aldo Eduardo
// 4CM14

import java.util.ArrayList;
import java.util.List;

public class Application {
    public static void main(String[] args) {
        Aggregator aggregator = new Aggregator();
        List<String> lista_tareas = new ArrayList<String>();
        List<String> lista_workers = new ArrayList<String>();

        // Este ciclo `for` está agregando URLs a la lista `lista_workers`. esta
        // iterando
        // tres veces y
        // concatenar el argumento `i`-ésimo pasado al programa con la cadena
        // `"http://"` y
        // `"/autobús"`. La cadena resultante se agrega luego a la lista
        // `lista_workers`.
        for (int i = 0; i < 3; i++)
            lista_workers.add("http://" + args[i] + "/buscar");

        // Este ciclo `for` itera sobre los argumentos restantes pasados ​​al
        // programa (iniciando
        // del cuarto argumento) y agregándolos a la lista `lista_tareas`. También
        // imprime cada
        // argumento a la consola usando `System.out.println()`.
        for (int i = 3; i < args.length; i++)
            lista_tareas.add(args[i]);

        // Este código llama al método `sendTasksToWorkers` del `Aggregator`
        // clase con dos
        // argumentos: `lista_workers` y `lista_tareas`. `sendTasksToWorkers`
        // se espera que el método
        // devuelve una lista de cadenas, que luego se asigna a la variable `resultados`
        // de
        // tipo
        // `Lista<Cadena>`.
        List<String> results = aggregator.sendTasksToWorkers(lista_workers, lista_tareas);

        for (String result : results)
            System.out.println("\t" + result);

    }
}