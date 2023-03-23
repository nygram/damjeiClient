package com;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import model.Empleats;

/**
 *
 * @author nygram
 */
public class Server {

    int puerto = 10000;
    boolean correcte = true;
    boolean administrador = false;
    int token = 234;
    int id = 1;
    HashMap<Integer, Integer> hash = new HashMap<>();
    ServerLogin login = new ServerLogin();

    public Server() {

    }

    public Server(int puerto) {
        this.puerto = puerto;
    }

    public void iniciar() throws IOException, SQLException {
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
            JsonObject objecte = gson.fromJson(json, JsonObject.class);

            int accio = objecte.get("accio").getAsInt();
            System.out.println(accio);
            switch (accio) {
                case 0:
                    correcte = login.Login(objecte, socket);
                    if (correcte) {
                        administrador = login.Admin(objecte, socket);
                        JsonObject obtResposta = new JsonObject();
                        obtResposta.addProperty("correcte", correcte);
                        obtResposta.addProperty("administrador", administrador);
                        obtResposta.addProperty("token", token);

                        String res = gson.toJson(obtResposta);
                        OutputStream sortida = socket.getOutputStream();
                        sortida.write(res.getBytes());
                        sortida.flush();
                        socket.close();
                    } else {
                        correcte = false;
                        JsonObject obtResposta = new JsonObject();
                        obtResposta.addProperty("correcte", correcte);
                        obtResposta.addProperty("administrador", administrador);
                        obtResposta.addProperty("token", token);

                        String res = gson.toJson(obtResposta);
                        OutputStream sortida = socket.getOutputStream();
                        sortida.write(res.getBytes());
                        sortida.flush();
                        socket.close();
                        
                    }
                    break;
                
                case 1:
                    int token = objecte.get("token").getAsInt();
                    
                    System.out.println(token);
            }
        }

    }
}
