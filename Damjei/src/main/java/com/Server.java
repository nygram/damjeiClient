/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.io.IOException;
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
    
    public Server(){
    
    }
    
    public Server(int puerto) {
        this.puerto = puerto;
    }
    
    public void iniciar() throws IOException{
        ServerSocket serverSocket = new ServerSocket(this.puerto);
        System.out.println("Connectat");
        while (true) {
            Socket socket = serverSocket.accept();
            Scanner scanner = new Scanner(socket.getInputStream());
            String json = scanner.nextLine();
            String json2 = "Correcte";
            Gson gson = new Gson();
            JsonObject objecte = gson.fromJson(json, JsonObject.class);
            Empleats empleat = gson.fromJson(objecte.get("empleat"), Empleats.class);
            int accio = objecte.get("accio").getAsInt();
            System.out.println(empleat.getNom());
            
            if (empleat.getNom() == "2"){
                OutputStream sortida = socket.getOutputStream();
                String resposta = "Usuari correcte";
                sortida.write(resposta.getBytes());
                sortida.flush();
            }
            
            System.out.println(accio);
            
            
        }
    }
    
}
