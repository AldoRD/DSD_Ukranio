import java.math.BigInteger;
import java.net.InetSocketAddress;
import com.sun.net.httpserver.*;
import java.util.concurrent.*;
import javax.management.ValueExp;
import java.util.*;
import java.io.*;

public class WebServer {

    private static final String TASK_ENDPOINT = "/task";
    private static final String STATUS_ENDPOINT = "/status";
    private static final String SERVICIOS_ENDPOINT = "/servicios";
    private static final String URL_SERVER_ONE = "http://localhost:8081/calcular";
    private static final String URL_SERVER_TWO = "http://localhost:8082/calcular";
    private final int port;
    private HttpServer server;

    public static void main(String[] args) {
        int serverPort = 8080;
        if (args.length == 1) {
            serverPort = Integer.parseInt(args[0]);
        }

        WebServer webServer = new WebServer(serverPort);
        webServer.startServer();

        System.out.println("Servidor escuchando en el puerto " + serverPort);
    }

    public WebServer(int port) {

        this.port = port;
    }

    public void startServer() {
        try {

            this.server = HttpServer.create(new InetSocketAddress(port), 0);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        HttpContext statusContext = server.createContext(STATUS_ENDPOINT);
        HttpContext taskContext = server.createContext(TASK_ENDPOINT);
        HttpContext serviciosContext = server.createContext(SERVICIOS_ENDPOINT);

        statusContext.setHandler(this::handleStatusCheckRequest);
        taskContext.setHandler(this::handleTaskRequest);
        serviciosContext.setHandler(this::handleServiciosRequest);

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
            long t = finishTime - startTime;
            long seconds = TimeUnit.NANOSECONDS.toSeconds(t);
            long mili = TimeUnit.NANOSECONDS.toMillis(t);
            mili = mili - seconds * 1000;

            String debugMessage = String.format("La operación tomó %d nanosegundos = %d segundos %d miliegundos.", t,
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

    private void handleServiciosRequest(HttpExchange exchange) throws IOException {
        System.out.println("1");

        // if (!exchange.getRequestMethod().equalsIgnoreCase("post")) {
        // exchange.close();
        // return;
        // }

        System.out.println("2");

        Headers headers = exchange.getRequestHeaders();
        boolean isDebugMode = false;

        if (headers.containsKey("X-Debug") && headers.get("X-Debug").get(0).equalsIgnoreCase("true")) {
            isDebugMode = true;
        }

        long startTime = System.nanoTime();

        System.out.println(exchange.getRequestURI().getRawQuery());
        String[] params = exchange.getRequestURI().getRawQuery().toString().split("&");
        byte[] responseBytes = servicios(params);

        long finishTime = System.nanoTime();

        if (isDebugMode) {
            long t = finishTime - startTime;
            long seconds = TimeUnit.NANOSECONDS.toSeconds(t);
            long mili = TimeUnit.NANOSECONDS.toMillis(t);
            mili = mili - seconds * 1000;

            String debugMessage = String.format("La operación tomó %d nanosegundos = %d segundos %d miliegundos.", t,
                    seconds, mili);
            exchange.getResponseHeaders().put("X-Debug-Info", Arrays.asList(debugMessage));
        }

        sendResponse(responseBytes, exchange);
    }

    private byte[] servicios(String[] params) {
        System.out.println("Funcion");
        int num = 0, servicio = 0;
        String result = "";

        if (params.length < 2)
            return String.format("Parametro insuficientes").getBytes();

        for (String param : params) {
            String[] values = param.split("=");
            if (values[0].equalsIgnoreCase("numero"))
                num = Integer.parseInt(values[1]);
            else
                servicio = Integer.parseInt(values[1]);
        }

        if (servicio == 1)
            result = sendToServers(URL_SERVER_ONE, num);
        if (servicio == 2)
            result = sendToServers(URL_SERVER_TWO, num);

        if (servicio == 3) {
            result += sendToServers(URL_SERVER_ONE, num);
            result += sendToServers(URL_SERVER_TWO, num);
        }

        return result.getBytes();
    }

    private String sendToServers(String WORKER_ADDRESS, int values) {
        Aggregator aggregator = new Aggregator();
        String response = "";

        List<String> results = aggregator.sendTasksToWorkers(Arrays.asList(WORKER_ADDRESS),
                Arrays.asList(String.valueOf(values)));

        for (String result : results) {
            System.out.println("\t" + result);
            response += result + "\n";
        }

        return response;
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