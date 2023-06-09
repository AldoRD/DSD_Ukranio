// Proyecto 4
// Ramirez Dominguez Aldo Eduardo
// 4CM14

import java.net.InetSocketAddress;
import com.sun.net.httpserver.*;
import java.util.concurrent.*;
import java.util.*;
import java.io.*;

public class WebServerSearch {
    private static final String STATUS_ENDPOINT = "/status";
    private static final String BUSCAR_ENDPOINT = "/buscar";

    private final int port;
    private HttpServer server;

    public static void main(String[] args) {
        int serverPort = 8080;

        if (args.length == 1)
            serverPort = Integer.parseInt(args[0]);

        WebServerSearch webServer = new WebServerSearch(serverPort);
        webServer.startServer();
        System.out.println("Servidor escuchando en el puerto " + serverPort);

    }

    public WebServerSearch(int port) {
        this.port = port;
    }

    /**
     * La función inicia un servidor HTTP con dos puntos finales y controladores de
     * estado
     * comprobar y buscar
     * peticiones.
     */
    public void startServer() {
        try {
            this.server = HttpServer.create(new InetSocketAddress(port), 0);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        HttpContext statusContext = server.createContext(STATUS_ENDPOINT);
        HttpContext findContext = server.createContext(BUSCAR_ENDPOINT);

        statusContext.setHandler(this::handleStatusCheckRequest);
        findContext.setHandler(this::handleBuscarRequest);

        server.setExecutor(Executors.newFixedThreadPool(8));
        server.start();
    }

    private void handleStatusCheckRequest(HttpExchange exchange) throws IOException {
        if (!exchange.getRequestMethod().equalsIgnoreCase("get")) {
            exchange.close();
            return;
        }

        String responseMessage = "El servidor está vivo\n";
        sendResponse(responseMessage.getBytes(), exchange);
    }

    private void handleBuscarRequest(HttpExchange exchange) throws IOException {
        if (!exchange.getRequestMethod().equalsIgnoreCase("post")) {
            exchange.close();
            return;
        }

        Headers headers = exchange.getRequestHeaders();
        boolean isDebugMode = false;

        if (headers.containsKey("X-Debug") && headers.get("X-Debug").get(0).equalsIgnoreCase("true")) {
            isDebugMode = true;
        }

        long startTime = System.nanoTime();

        byte[] requestBytes = exchange.getRequestBody().readAllBytes();
        byte[] responseBytes = buscarEnTexto(requestBytes);

        long finishTime = System.nanoTime();

        if (isDebugMode) {
            long t = finishTime - startTime;
            long seconds = TimeUnit.NANOSECONDS.toSeconds(t);
            long mili = TimeUnit.NANOSECONDS.toMillis(t);
            mili = mili - seconds * 1000;

            String debugMessage = "Tiempo: " + seconds + " sec " + mili + " milis";
            exchange.getResponseHeaders().put("X-Debug-Info", Arrays.asList(debugMessage));
        }
        sendResponse(responseBytes, exchange);
    }

    private byte[] buscarEnTexto(byte[] requestBytes) {
        String bodyString = new String(requestBytes);
        String[] stringParametros = bodyString.split(",");
        String palabra = stringParametros[0];
        int contador = 0;

        String contenido = obtenerTexto();
        String[] palabras = contenido.toString().split(" ");

        // Este código está iterando a través de una serie de palabras llamadas
        // `palabras` y
        // comprobando si cada palabra
        // es igual a una `palabra` dada. Si se encuentra una coincidencia, la variable
        // `contador`
        // se incrementa.
        // Esto se usa para contar el número de veces que aparece la `palabra` dada en
        // una
        // texto.
        for (String p : palabras) {
            if (p.equals(palabra))
                contador = contador + 1;
        }

        String resultado = "Veces encontrada la palabra " + palabra + ": " + contador + "\n";
        System.out.println(resultado);
        return resultado.getBytes();
    }

    /**
     * Esta función lee el contenido de un archivo de texto y lo devuelve como una
     * cadena.
     *
     * @return El método devuelve un String que contiene el contenido de un texto
     *         archivo llamado
     *         "BIBLIA_COMPLETA.txt".
     */
    private String obtenerTexto() {
        String rutaArchivo = "BIBLIA_COMPLETA.txt";
        StringBuilder contenido = new StringBuilder();

        try (BufferedReader br = new BufferedReader(new FileReader(rutaArchivo))) {
            // Este código lee el contenido de un archivo de texto línea por línea y agrega
            // cada línea a
            // un objeto StringBuilder llamado `contenido`. El método `readLine()` devuelve
            // un
            // Cadena
            // que contiene la siguiente línea de texto del archivo, y el ciclo continúa
            // hasta
            // hay
            // no hay más líneas para leer (es decir, `readLine()` devuelve nulo). El
            // `añadir()`
            // se utiliza el método
            // para agregar cada línea al objeto `contenido` StringBuilder, y el `"\n"`
            // el personaje es
            // agregado al final de cada línea para preservar los saltos de línea en el
            // original
            // Archivo de texto.
            // Finalmente, el objeto `BufferedReader` se cierra usando el método `close()`.
            String linea;
            while ((linea = br.readLine()) != null) {
                contenido.append(linea).append("\n");
            }
            br.close();
        } catch (IOException e) {
            System.out.println("Error al leer el archivo: " + e.getMessage());
        }
        return contenido.toString();
    }

    private void sendResponse(byte[] responseBytes, HttpExchange exchange) throws IOException {
        exchange.sendResponseHeaders(200, responseBytes.length);
        OutputStream outputStream = exchange.getResponseBody();
        outputStream.write(responseBytes);
        outputStream.flush();
        outputStream.close();
        exchange.close();
    }
}
