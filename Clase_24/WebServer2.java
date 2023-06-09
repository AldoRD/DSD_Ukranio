import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.math.BigInteger;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.*;
import java.io.*;

public class WebServer2 {
    private static final String TASK_ENDPOINT = "/task"; // End point Post
    private static final String SEARCH_ENDPOINT = "/searchtoken"; // End point Post
    private static final String STATUS_ENDPOINT = "/status";// End point Get

    private final int port; // Puerto
    private HttpServer server; // Servidor basico HTTP

    public static void main(String[] args) {
        int serverPort = 8080; // Puerto Default del servidor
        if (args.length == 1) { // Se pasa por linea de comandos
            serverPort = Integer.parseInt(args[0]);
        }

        WebServer2 webServer = new WebServer2(serverPort);// Objeto webServer
        webServer.startServer(); // Inicializa la configuracion del servidor
        // Imprime puerto en el cual esta escuchando el servidor
        System.out.println("Servidor escuchando en el puerto " + serverPort);
    }

    // Contructor
    public WebServer2(int port) {
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
        HttpContext searchToken = server.createContext(SEARCH_ENDPOINT);

        statusContext.setHandler(this::handleStatusCheckRequest);
        searchToken.setHandler(this::handleTaskRequest);

        server.setExecutor(Executors.newFixedThreadPool(8));
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
        // byte[] responseBytes = calculateResponse(requestBytes);
        String count = generadorPalabras(requestBytes);

        long finishTime = System.nanoTime();

        if (isDebugMode) {
            long t = finishTime - startTime; // Tiempo procesamiento en nanos
            long seconds = TimeUnit.NANOSECONDS.toSeconds(t);
            long mili = TimeUnit.NANOSECONDS.toMillis(t);
            mili = mili - seconds * 1000;

            String debugMessage = String.format(
                    "La operación tomó %d nanosegundos = %d segundos con %d miliegundos.", t,
                    seconds, mili);
            exchange.getResponseHeaders().put("X-Debug-Info", Arrays.asList(debugMessage));
        }

        sendResponse(count.getBytes(), exchange);
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

    public static String generadorPalabras(byte[] requestBytes) {

        String bodyString = new String(requestBytes)
        String[] stringNumbers = bodyString.split(",");

        BigInteger result = BigInteger.ONE;
        ArrayList<String> data = new ArrayList<String>();

        for (String number : stringNumbers) {
            data.add(number);
        }

        int times = Integer.parseInt(data.get(0));
        int longitud = 3;
        String banco = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String cadena = "";

        StringBuilder cadenota = new StringBuilder();
        for (int i = 0; i < times; i++) {
            cadena = "";
            for (int x = 0; x < longitud; x++) {
                int indiceAleatorio = numeroAleatorioEnRango(0, banco.length() - 1);
                char caracterAleatorio = banco.charAt(indiceAleatorio);
                cadena += caracterAleatorio;
            }
            cadena += " ";
            cadenota.append(cadena);
        }

        int count = buscarToken(cadenota, data.get(1));

        return "Veces encontradas [" + data.get(1) + "]: " + count;
    }

    public static int numeroAleatorioEnRango(int minimo, int maximo) {
        return ThreadLocalRandom.current().nextInt(minimo, maximo + 1);
    }

    public static int buscarToken(StringBuilder cadena, String token) {
        int count = 0;
        int lastIndex = 0;
        String temp = token;

        while (lastIndex >= 0) {
            lastIndex = cadena.indexOf(temp + " ", lastIndex);
            if (lastIndex >= 0) {
                count += 1;
                lastIndex += 4;
            }
        }
        return count;
    }
}