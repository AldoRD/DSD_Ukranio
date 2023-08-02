
package com.mycompany.app;

import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.math.BigInteger;
import java.net.InetSocketAddress;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import com.fasterxml.jackson.databind.DeserializationFeature;   
import com.fasterxml.jackson.databind.ObjectMapper;             
import com.fasterxml.jackson.databind.PropertyNamingStrategy;   

public class WebServer {
   
    private static final String STATUS_ENDPOINT = "/status";
    private static final String HOME_PAGE_ENDPOINT = "/";
    private static final String HOME_PAGE_UI_ASSETS_BASE_DIR = "/ui_assets/";
    private static final String ENDPOINT_PROCESS = "/procesar_datos";

    private final int port; 
    private HttpServer server; 
    private final ObjectMapper objectMapper;

    public WebServer(int port) {
        this.port = port;
        this.objectMapper = new ObjectMapper();
        this.objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public void startServer() {
        try {
            this.server = HttpServer.create(new InetSocketAddress(port), 0);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        HttpContext statusContext = server.createContext(STATUS_ENDPOINT); 
        HttpContext taskContext = server.createContext(ENDPOINT_PROCESS);
        HttpContext homePageContext = server.createContext(HOME_PAGE_ENDPOINT);
        statusContext.setHandler(this::handleStatusCheckRequest);
        taskContext.setHandler(this::handleTaskRequest);
        homePageContext.setHandler(this::handleRequestForAsset);

        server.setExecutor(Executors.newFixedThreadPool(8));
        server.start();
    }

    private void handleRequestForAsset(HttpExchange exchange) throws IOException {
        if (!exchange.getRequestMethod().equalsIgnoreCase("get")) {
            exchange.close();
            return;
        }
        byte[] response;
        
        String asset = exchange.getRequestURI().getPath(); 
        if (asset.equals(HOME_PAGE_ENDPOINT)) { 
            response = readUiAsset(HOME_PAGE_UI_ASSETS_BASE_DIR + "index.html");
        } else {
            response = readUiAsset(asset); 
        }
        addContentType(asset, exchange);
        sendResponse(response, exchange);
    }

    private byte[] readUiAsset(String asset) throws IOException {
        //System.out.println(asset);
        InputStream assetStream = getClass().getResourceAsStream(asset);

        if (assetStream == null) {
            return new byte[]{};
        }
        return assetStream.readAllBytes(); 
    }

    private static void addContentType(String asset, HttpExchange exchange) {

        String contentType = "text/html";  
        if (asset.endsWith("js")) {
            contentType = "text/javascript";
        } else if (asset.endsWith("css")) {
            contentType = "text/css";
        }
        exchange.getResponseHeaders().add("Content-Type", contentType);
    }

    private void handleTaskRequest(HttpExchange exchange) throws IOException {
        if (!exchange.getRequestMethod().equalsIgnoreCase("post")) { 
            exchange.close();
            return;
        }
        // System.out.println("Se cuenta el número de palabras. Metodo POST");
        try {
            FrontendSearchRequest frontendSearchRequest = objectMapper.readValue(exchange.getRequestBody().readAllBytes(), FrontendSearchRequest.class); 
            String frase = frontendSearchRequest.getSearchQuery();
            System.out.println("El numero n recibido es: "+frase); //lo que se recibe del usuario
            StringTokenizer st = new StringTokenizer(frase);

            //Obtenemos el número del que obtendremos factorial
            BigInteger numero = new BigInteger(frase);
            BigInteger divisor = new BigInteger("3");
            BigInteger rangoPorServer = numero.divide(divisor); 

            //Generamos rangos
            String rango1 = new String("1-"+rangoPorServer);
            String rango2 = new String(rangoPorServer.add(BigInteger.ONE)+"-"+rangoPorServer.multiply(BigInteger.TWO));
            String rango3 = new String(rangoPorServer.multiply(BigInteger.TWO).add(BigInteger.ONE)+"-"+numero);

            WebClient webClient = new WebClient();
            byte[] requestPayload1 = rango1.getBytes();
            byte[] requestPayload2 = rango2.getBytes();
            byte[] requestPayload3 = rango3.getBytes();

            CompletableFuture<String>[] futures = new CompletableFuture[3];
            List<String> results = new ArrayList<>();
            futures[0] = webClient.sendTask("http://localhost:8081/servidor_procesamiento", requestPayload1);
            futures[1] = webClient.sendTask("http://localhost:8082/servidor_procesamiento", requestPayload2);
            futures[2] = webClient.sendTask("http://localhost:8083/servidor_procesamiento", requestPayload3);
            for(int i = 0; i < futures.length; i++){
                results.add(futures[i].join());
            }
            
            System.out.println("Respuesta de los servidores: " + results);

            BigInteger factorial = BigInteger.valueOf(1);

            for(String fact : results){
                BigInteger factBigInt = new BigInteger(fact);
                factorial = factorial.multiply(factBigInt);
            }

            System.out.println("El resultado de la multiplicación es: " + factorial);
            frase = factorial.toString();
            
            FrontendSearchResponse frontendSearchResponse = new FrontendSearchResponse(frase, st.countTokens());
            byte[] responseBytes = objectMapper.writeValueAsBytes(frontendSearchResponse);
            sendResponse(responseBytes, exchange);

        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
    }

    private void handleStatusCheckRequest(HttpExchange exchange) throws IOException {
        if (!exchange.getRequestMethod().equalsIgnoreCase("get")) {
            exchange.close();
            return;
        }
        //System.out.println("Se reporta el estado del servidor. Metodo get");
        String responseMessage = "El servidor está vivo\n";
        sendResponse(responseMessage.getBytes(), exchange);
    }

    private void sendResponse(byte[] responseBytes, HttpExchange exchange) throws IOException {
        //System.out.println("El peso de la respuesta en bytes es: " + responseBytes.length);
        exchange.sendResponseHeaders(200, responseBytes.length);
        OutputStream outputStream = exchange.getResponseBody();
        outputStream.write(responseBytes);
        outputStream.flush();
        outputStream.close();
    }

}


