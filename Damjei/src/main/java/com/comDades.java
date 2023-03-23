/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 *
 * @author Javi
 */
public class comDades{

    public comDades(){
        
    }

    public void enviaDades(JsonObject object, Socket socket) throws IOException {
        
        Gson gson = new Gson();

        String json = gson.toJson(object);
        OutputStream sortida = socket.getOutputStream();
        sortida.write(json.getBytes());
        
        
    }

    public JsonObject repDades(Socket socket) throws IOException {
        
        Gson gson = new Gson();
        InputStream entrada = socket.getInputStream();
        byte[] buffer = new byte[1024];
        int bytesRead = entrada.read(buffer);
        String resposta = new String(buffer, 0, bytesRead);
        System.out.println(resposta);
        JsonObject objecte = gson.fromJson(resposta, JsonObject.class);
        return objecte;
        
    }
}
