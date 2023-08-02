package com.mycompany.app;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;

public class WebClient {
    private HttpClient client;

    public WebClient() {
        this.client = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_1_1)
                .build();
    }

    public CompletableFuture<String> sendTask(String url, byte[] requestPayload) { //RECIBE LA DIRECCIÓN DE LA CONEXIÓN Y LOS DATOS A ENVIAR AL SERVIDOR
        HttpRequest request = HttpRequest.newBuilder() //PARA LA CONSTRUCCIÓN DE LA SOLICITUD HTTP
                .POST(HttpRequest.BodyPublishers.ofByteArray(requestPayload))
                .uri(URI.create(url))
                .build();

        return client.sendAsync(request, HttpResponse.BodyHandlers.ofString()) //SE ENVÍA LA SOLICITUD DE MANERA ASÍNCRONA
                .thenApply(respuesta -> { return respuesta.body().toString();});
    }
}