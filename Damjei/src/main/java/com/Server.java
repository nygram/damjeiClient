package com;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Scanner;
import model.Empleats;

/**
 *
 * @author nygram
 */
public class Server {

    int puerto = 10000;

    public Server() {

    }

    public Server(int puerto) {
        this.puerto = puerto;
    }

    public void iniciar() throws IOException {
        ServerSocket serverSocket = new ServerSocket(puerto);
        System.out.println("Iniciat");
        while (true) {
            Socket socket = serverSocket.accept();
            System.out.println("Connectat");
            InputStream inputStream = socket.getInputStream();
            byte[] buffer = new byte[1024];
            int count = inputStream.read(buffer);
            Gson gson = new Gson();
            String json = new String(buffer, 0, count);
            System.out.println(json);
            JsonObject objecte = gson.fromJson(json, JsonObject.class);
            Empleats empleat = gson.fromJson(objecte.get("empleat"), Empleats.class);
            int accio = objecte.get("accio").getAsInt();
            String nom = empleat.getNom();

            System.out.println(json.getClass());

            if (nom.equals("2")) {
                String resposta = "Usuari correcte";
                System.out.println("Usuari Correcte");
                OutputStream sortida = socket.getOutputStream();
                sortida.write(resposta.getBytes());
                sortida.flush();
            } else {
                System.out.println("no se cumple");
            }

        }
    }

}
