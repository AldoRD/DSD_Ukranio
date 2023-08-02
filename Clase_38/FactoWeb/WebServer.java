import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Executors;
public class WebServer {
    ArrayList<String> ListaCURP = new ArrayList<>(); 
    int curpPorSeg;
    //End point
    private static final String STATUS_ENDPOINT = "/status";
    private static final String SAVECURP_ENDPOINT = "/savecurp";
    private static final String SERVIDORPROCESAMIENTO_ENDPOINT = "/servidor_procesamiento";

    private final int port; //Puerto
    private HttpServer server; //Tipo http
    public static void main(String[] args) {
        int serverPort = 8080;
        if (args.length == 1) { //Puerto especifico
            serverPort = Integer.parseInt(args[0]);
        }
        WebServer webServer = new WebServer(serverPort); //Instancia un webServer
        webServer.startServer();//Ejecuta el servidor
        System.out.println("Servidor escuchando en el puerto " + serverPort);
    }
    public WebServer(int port) { //Contructor
        this.port = port;
    }
    public void startServer() {
        try {
            this.server = HttpServer.create(new InetSocketAddress(port), 0);//Crea una intancia de TCP, la cola esta en 0(lo decide el sistema)
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        //Asocia un contexto a cada ruta
        HttpContext statusContext = server.createContext(STATUS_ENDPOINT);
        HttpContext savecurpContext = server.createContext(SAVECURP_ENDPOINT);
        HttpContext servidorProcesamientoContext = server.createContext(SERVIDORPROCESAMIENTO_ENDPOINT);
        statusContext.setHandler(this::handleStatusCheckRequest);
        savecurpContext.setHandler(this::handleSaveCurpRequest);
        servidorProcesamientoContext.setHandler(this::handleServidorProcesamientoRequest);
        server.setExecutor(Executors.newFixedThreadPool(8)); //Asigna un ejecutor al servidor con un pool de 8 hilos
        server.start(); //Inicia la ejecución de un hilo
    }

    private void handleServidorProcesamientoRequest(HttpExchange exchange) throws IOException {
        if (!exchange.getRequestMethod().equalsIgnoreCase("post")) { //Revisa que sea metodo post
            exchange.close();
            return;
        }
        //System.out.println("\nSe recibió solicitud del Servidor Web...");

        byte[] requestBytes = exchange.getRequestBody().readAllBytes();
        String cadena = new String(requestBytes); //caracter 11 y 12 (contando desde 0)
        System.out.println("El rango recibido es: " + cadena);
        //String responseMessage = "Soy el servidor de procesamiento, y recibi: "+cadena;

        //Obteniendo números
        String[] rangos = cadena.split("-");
        BigInteger num1 = new BigInteger(rangos[0]);
        BigInteger num2 = new BigInteger(rangos[1]);
        BigInteger fact = new BigInteger("1");
        
        for (BigInteger bi = num1; bi.compareTo(num2) <= 0; bi = bi.add(BigInteger.ONE)){
            fact = fact.multiply(bi);
        }
        
        System.out.println("El resultado de la multiplicación es: "+fact);
        //System.out.println("Enviando factorial...");

        String responseMessage = fact.toString();
        
        sendResponse(responseMessage.getBytes(), exchange); //Envia la repuesta
        
    }

    private void handleSaveCurpRequest(HttpExchange exchange) throws IOException {
        if (!exchange.getRequestMethod().equalsIgnoreCase("post")) { //Revisa que sea metodo post
            exchange.close();
            return;
        }
        //Headers headers = exchange.getRequestHeaders(); //Obtiene las cabeceras
        byte[] requestBytes = exchange.getRequestBody().readAllBytes();
        String curp = new String(requestBytes);
        //System.out.println("El curp recibido es..."+curp);

        ListaCURP.add(curp);

        String responseMessage = "OK";
        sendResponse(responseMessage.getBytes(), exchange); //Envia la repuesta
        
    }

    ///Metodo para ruta status
    private void handleStatusCheckRequest(HttpExchange exchange) throws IOException {
        System.out.println("a");
        if (!exchange.getRequestMethod().equalsIgnoreCase("get")) { //Revisa que sea metodo get
            exchange.close();
            return;
        }
        String responseMessage = "El servidor está wapo wapo wapote\n";
        sendResponse(responseMessage.getBytes(), exchange); //Envia la repuesta
    }

    ///Metodo paarr responder
    private void sendResponse(byte[] responseBytes, HttpExchange exchange) throws IOException {
        exchange.sendResponseHeaders(200, responseBytes.length);
        OutputStream outputStream = exchange.getResponseBody();
        outputStream.write(responseBytes);
        outputStream.flush();
        outputStream.close();
        exchange.close();
    }
}