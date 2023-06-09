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

import java.util.Arrays;
import java.util.List;

public class Application {
    private static final String WORKER_ADDRESS_1 = "http://localhost:8081/search";
    private static final String WORKER_ADDRESS_2 = "http://localhost:8082/search";

    public static void main(String[] args) {
        // El código está creando una instancia de la clase `Aggregator` e inicializando
        // tres `String`
        // variables `tarea1`, `tarea2` y `tarea3` con algunos valores.
        Aggregator aggregator = new Aggregator();
        String task1 = "175760,IPN";
        String task2 = "1757600,SAL";
        String task3 = "700000,MAS";

        // El código está llamando al método `sendTasksToWorkers` de la clase
        // `Aggregator`, pasando
        // dos argumentos: una lista de direcciones de trabajadores (`WORKER_ADDRESS_1`
        // y `WORKER_ADDRESS_2`) y una
        // lista de tareas (`tarea1`, `tarea2` y `tarea3`). El método envía cada tarea a
        // uno de los
        // trabajadores y espera a que se devuelvan los resultados. Los resultados se
        // almacenan en una lista de
        // cadenas llamadas `resultados`.
        List<String> results = aggregator.sendTasksToWorkers(Arrays.asList(WORKER_ADDRESS_1, WORKER_ADDRESS_2),
                Arrays.asList(task1, task2, task3));

        for (String result : results) {
            System.out.println("\t" + result);
        }
    }
}