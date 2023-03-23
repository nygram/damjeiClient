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
 * Classe que gestiona enviar i rebre dades del servidor
 * @author Javi
 * 
 */

public class comDades{

    /**
     *
     */
    public comDades(){
        
    }
    /**
     * Métode que envia al servidor un objecte de tipus Json fent servir el socket 
     * que rep com a paàmetre
     * @param object tipus JsonObject. Rep les dades encapsulades per enviar
     * @param socket per comunicar amb el servidor
     * @throws IOException 
     */
    public void enviaDades(JsonObject object, Socket socket) throws IOException {
        
        Gson gson = new Gson();

        String json = gson.toJson(object);
        OutputStream sortida = socket.getOutputStream();
        sortida.write(json.getBytes());
        
        
    }
    /**
     * Métode que rep les dades del servidor i les encapsula en un objecte Json
     * @param socket per comunicar amb el servidor
     * @return JsonObject on estan encapsulades les dades rebudes del servidor
     * @throws IOException 
     */
    public JsonObject repDades(Socket socket) throws IOException {
        
        Gson gson = new Gson();
        InputStream entrada = socket.getInputStream();
        byte[] buffer = new byte[1024];
        int bytesRead = entrada.read(buffer);
        String resposta = new String(buffer, 0, bytesRead);
        JsonObject objecte = gson.fromJson(resposta, JsonObject.class);
        return objecte;
        
    }
}
