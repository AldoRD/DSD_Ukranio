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

    public List<String> sendTasksToWorkers(List<String> workersAddresses, List<byte[]> tasks) {
        int tamWorkers = workersAddresses.size();
        CompletableFuture<String>[] futures = new CompletableFuture[tamWorkers];

        for (int i = 0; i < tamWorkers; i++) {
            String workerAddress = workersAddresses.get(i);
            byte[] requestPayload = tasks.get(i);

            futures[i] = webClient.sendTask(workerAddress, requestPayload);
        }

        boolean bandera = true;
        while (bandera) {
            for (int j = 0; j < tamWorkers; j++) {
                if (true == futures[j].isDone())
                    bandera = false;
            }
        }

        List<String> results = new ArrayList();
        for (int i = 0; i < tasks.size(); i++) {
            results.add(futures[i].join());
        }

        return results;
    }
}