
//Bibliotecas
import java.math.BigInteger;
import java.net.InetSocketAddress;
import com.sun.net.httpserver.*;
import java.util.concurrent.*;
import java.util.*;
import java.io.*;

public class WebServer {
    // --------------------|Variables de Instancia|--------------
    private static final String TASK_ENDPOINT = "/task"; // End point task
    private static final String STATUS_ENDPOINT = "/status";// End point status
    private static final String SEARCH_ENDPOINT = "/search";// End point search
    private final int port; // Puerto
    private HttpServer server; // Servidor basico HTTP

    // --------------------|Metodo Principal|--------------------
    public static void main(String[] args) {
        int serverPort = 8080; // Puerto Default del servidor
        if (args.length == 1) { // Se pasa por linea de comandos
            serverPort = Integer.parseInt(args[0]);
        }

        WebServer webServer = new WebServer(serverPort); // Objeto webServer
        // Inicializa la configuracion del servidor
        webServer.startServer();
        // Imprime puerto en el cual esta escuchando el servidor
        System.out.println("Servidor escuchando en el puerto " + serverPort);
    }

    // Contructor
    public WebServer(int port) {
        // Se inicializa con el puerto ingresado
        this.port = port;
    }

    // --------------------|Inicio sel servidor|-----------------
    public void startServer() {
        try {
            // Crea una instancia socket TCP que se vincula a una ip llamada create en
            // puerto port
            // '0' es el tamanio de una lista de solicitudes pendientes para mantener en
            // cola de
            // espera
            this.server = HttpServer.create(new InetSocketAddress(port), 0);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        // Contextos
        HttpContext statusContext = server.createContext(STATUS_ENDPOINT);
        HttpContext taskContext = server.createContext(TASK_ENDPOINT);
        HttpContext searchContext = server.createContext(SEARCH_ENDPOINT);

        statusContext.setHandler(this::handleStatusCheckRequest);
        taskContext.setHandler(this::handleTaskRequest);
        searchContext.setHandler(this::handleSearchRequest);
        // Creacion de hilos
        server.setExecutor(Executors.newFixedThreadPool(8));
        server.start();
    }

    // --------------------|Status|------------------------------
    private void handleStatusCheckRequest(HttpExchange exchange) throws IOException {
        if (!exchange.getRequestMethod().equalsIgnoreCase("get")) {
            exchange.close();
            return;
        }

        String responseMessage = "El servidor está vivo\n";
        sendResponse(responseMessage.getBytes(), exchange);
    }

    // --------------------|Task|--------------------------------
    private void handleTaskRequest(HttpExchange exchange) throws IOException {

        if (!exchange.getRequestMethod().equalsIgnoreCase("post")) {
            exchange.close();
            return;
        }

        Headers headers = exchange.getRequestHeaders();
        if (headers.containsKey("X-Test") && headers.get("X-Test").get(0).equalsIgnoreCase("true")) {
            String dummyResponse = "123\n";
            sendResponse(dummyResponse.getBytes(), exchange);
            return;
        }

        boolean isDebugMode = false;
        if (headers.containsKey("X-Debug") && headers.get("X-Debug").get(0).equalsIgnoreCase("true")) {
            isDebugMode = true;
        }

        long startTime = System.nanoTime();

        byte[] requestBytes = exchange.getRequestBody().readAllBytes();
        byte[] responseBytes = calculateResponse(requestBytes);

        long finishTime = System.nanoTime();

        if (isDebugMode) {
            long t = finishTime - startTime; // Tiempo procesamiento en nanos
            long seconds = TimeUnit.NANOSECONDS.toSeconds(t);
            long mili = TimeUnit.NANOSECONDS.toMillis(t);
            mili = mili - seconds * 1000;

            String debugMessage = String.format("La operación tomó %d nanosegundos = %d segundos %d miliegundos.", t,
                    seconds, mili);
            // String debugMessage = String.format("La operación tomó %d nanosegundos.",t);
            exchange.getResponseHeaders().put("X-Debug-Info", Arrays.asList(debugMessage));
        }

        sendResponse(responseBytes, exchange);
    }

    private byte[] calculateResponse(byte[] requestBytes) {
        BigInteger result = BigInteger.ONE;

        System.out.println("Datos antes: " + requestBytes);
        Demo obj = (Demo) SerializationUtils.deserialize(requestBytes);
        System.out.println("Datos despues: " + obj);

        BigInteger num1 = new BigInteger(obj.a);
        BigInteger num2 = new BigInteger(obj.b);

        result = result.multiply(num1);
        result = result.multiply(num2);

        return String.format("\n\nEl resultado de la multiplicación es %s\n", result).getBytes();
    }

    // --------------------|Search|------------------------------
    private void handleSearchRequest(HttpExchange exchange) throws IOException {
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
        byte[] responseBytes = buscarCadenota(requestBytes);

        long finishTime = System.nanoTime();

        if (isDebugMode) {
            long t = finishTime - startTime; // Tiempo procesamiento en nanos
            long seconds = TimeUnit.NANOSECONDS.toSeconds(t);
            long mili = TimeUnit.NANOSECONDS.toMillis(t);
            mili = mili - seconds * 1000;

            String debugMessage = String.format("La operación tomó %d nanosegundos = %d segundos %d miliegundos.", t,
                    seconds, mili);
            exchange.getResponseHeaders().put("X-Debug-Info", Arrays.asList(debugMessage));
        }

        sendResponse(responseBytes, exchange);
    }

    // Metodo para buscar cadena
    private byte[] buscarCadenota(byte[] requestBytes) {
        // Variables
        String bodyString = new String(requestBytes);
        String[] stringParametros = bodyString.split(",");
        int tokens = Integer.parseInt(stringParametros[0]); // total tokens
        String palabra = stringParametros[1]; // palabra a buscar
        int ocurrencias = 0;

        // Generacion de cadenas de 3 caracteres
        StringBuilder cadenota = new StringBuilder();
        for (int i = 0; i < tokens; i++) {
            cadenota.append(crearCadena() + " ");
        }

        // Busqueda de palabra
        int index = cadenota.indexOf(palabra); // Primera ocurrencia
        while (index >= 0) {
            index = cadenota.indexOf(palabra, index + 1);
            ocurrencias++;
        }
        return String.format("Ocurrencias de %s = %d\n", palabra, ocurrencias).getBytes();
    }

    // Generacion aleatoria de 3 caracteres
    private String crearCadena() {
        String aux = "";
        Random nrandom = new Random();

        for (int i = 0; i < 3; i++) {
            aux = aux + (char) (nrandom.nextInt(26) + 'A');
        }
        return aux.toString();
    }

    // --------------------|Respuesta|---------------------------
    private void sendResponse(byte[] responseBytes, HttpExchange exchange) throws IOException {
        exchange.sendResponseHeaders(200, responseBytes.length);
        OutputStream outputStream = exchange.getResponseBody();
        outputStream.write(responseBytes);
        outputStream.flush();
        outputStream.close();
        exchange.close();
    }
}