import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.math.BigInteger;
import java.net.InetSocketAddress;
import java.util.Arrays;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.*;
import java.io.*;

public class WebServer {
    private static final String TASK_ENDPOINT = "/task"; // End point Post
    private static final String STATUS_ENDPOINT = "/status";// End point Get

    private final int port; // Puerto
    private HttpServer server; // Servidor basico HTTP

    public static void main(String[] args) {
        int serverPort = 8080; // Puerto Default del servidor
        if (args.length == 1) { // Se pasa por linea de comandos
            serverPort = Integer.parseInt(args[0]);
        }

        WebServer webServer = new WebServer(serverPort);// Objeto webServer
        webServer.startServer(); // Inicializa la configuracion del servidor
        // Imprime puerto en el cual esta escuchando el servidor
        System.out.println("Servidor escuchando en el puerto " + serverPort);
    }

    // Contructor
    public WebServer(int port) {
        this.port = port;// Se inicializa con el puerto ingresado
    }

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

        HttpContext statusContext = server.createContext(STATUS_ENDPOINT);
        HttpContext taskContext = server.createContext(TASK_ENDPOINT);

        statusContext.setHandler(this::handleStatusCheckRequest);
        taskContext.setHandler(this::handleTaskRequest);

        server.setExecutor(Executors.newFixedThreadPool(1));
        server.start();
    }

    private void handleTaskRequest(HttpExchange exchange) throws IOException {

        if (!exchange.getRequestMethod().equalsIgnoreCase("post")) {
            exchange.close();
            return;
        }

        Headers headers = exchange.getRequestHeaders();
        if (headers.containsKey("X-Test") && headers.get("X-Test").get(0).equalsIgnoreCase("true")) {
            String str = "Numero de Headers: " + headers.size() + "\n";
            for (Map.Entry<String, List<String>> e : headers.entrySet()) {
                str += "Key: " + e.getKey() + " Value: " + e.getValue() + "\n";
            }
            System.out.println(str);
            sendResponse(str.getBytes(), exchange);
            return;
        }

        boolean isDebugMode = false;
        if (headers.containsKey("X-Debug") && headers.get("X-Debug").get(0).equalsIgnoreCase("true")) {
            isDebugMode = true;
        }

        long startTime = System.nanoTime();

        byte[] requestBytes = exchange.getRequestBody().readAllBytes();
        byte[] responseBytes = calculateResponse(requestBytes);

        try {
            Thread.sleep(5000);
        } catch (InterruptedException ex) {

        }

        long finishTime = System.nanoTime();

        if (isDebugMode) {
            long t = finishTime - startTime; // Tiempo procesamiento en nanos
            long seconds = TimeUnit.NANOSECONDS.toSeconds(t);
            long mili = TimeUnit.NANOSECONDS.toMillis(t);
            mili = mili - seconds * 1000;

            String debugMessage = String.format(
                    "La operacion tomo %d nanosegundos = %d segundos con %d miliegundos.", t,
                    seconds, mili);
            exchange.getResponseHeaders().put("X-Debug-Info", Arrays.asList(debugMessage));
        }

        sendResponse(responseBytes, exchange);
    }

    private byte[] calculateResponse(byte[] requestBytes) {
        String bodyString = new String(requestBytes);
        String[] stringNumbers = bodyString.split(",");

        BigInteger result = BigInteger.ONE;

        for (String number : stringNumbers) {
            BigInteger bigInteger = new BigInteger(number);
            result = result.multiply(bigInteger);
        }

        return String.format("El resultado de la multiplicación es %s\n", result).getBytes();
    }

    private void handleStatusCheckRequest(HttpExchange exchange) throws IOException {
        if (!exchange.getRequestMethod().equalsIgnoreCase("get")) {
            exchange.close();
            return;
        }

        String responseMessage = "El servidor está vivo\n";
        sendResponse(responseMessage.getBytes(), exchange);
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