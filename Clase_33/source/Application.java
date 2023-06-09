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
    private static final String WORKER_ADDRESS_1 = "http://localhost:8080/task";

    public static void main(String[] args) {

        Aggregator aggregator = new Aggregator();
        Demo obj = new Demo(args[0], args[1]);

        System.out.println("Antes: " + obj);
        byte[] task1 = SerializationUtils.serialize(obj);

        System.out.println("Objeto antes de ser enviado: " + task1);

        List<String> results = aggregator.sendTasksToWorkers(Arrays.asList(WORKER_ADDRESS_1), Arrays.asList(task1));

        for (String result : results) {
            System.out.println("\t" + result);
        }
    }
}