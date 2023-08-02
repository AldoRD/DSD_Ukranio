package com.mycompany.app;

import java.io.IOException;

public class App {

    public static void main(String[] args) throws IOException, InterruptedException {
        int currentServerPort = 3000;
        if (args.length == 1) {
            currentServerPort = Integer.parseInt(args[0]);
        }
        App application = new App();

        WebServer webServer = new WebServer(currentServerPort);
        webServer.startServer();

        System.out.println("Servidor escuchando en el puerto: " + currentServerPort);
    }
}