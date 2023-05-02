/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import javax.swing.JOptionPane;
import model.Empleats;

/**
 * Classe que gestiona enviar i rebre dades del servidor
 *
 * @author Javi
 *
 */
public class comDades {

    public comDades() {
    }

    /**
     * Métode que envia al servidor un objecte de tipus Json fent servir el
     * socket que rep com a paàmetre
     *
     * @param object tipus JsonObject. Rep les dades encapsulades per enviar
     * @param socket per comunicar amb el servidor
     * @throws IOException
     */
    public void enviaDades(JsonObject object, Socket socket) throws IOException {

        PrintWriter output = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
        output.println(object);

    }

    /**
     * Métode que rep les dades del servidor i les encapsula en un objecte Json
     *
     * @param socket per comunicar amb el servidor
     * @return JsonObject on estan encapsulades les dades rebudes del servidor
     * @throws IOException
     */
    public JsonObject repDades(Socket socket) throws IOException {

        Gson gson = new Gson();

        BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        String data = input.readLine();

        JsonObject objecte = gson.fromJson(data, JsonObject.class);
        return objecte;

    }

    public Object[] repDades2(Socket socket) throws IOException {

        Gson gson = new Gson();

        BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        String data = input.readLine();
        Object[] objects = gson.fromJson(data, Object[].class);
        System.out.println(objects);
        
        return objects;

    }
    
     public JsonArray repDades4(Socket socket) throws IOException {

        Gson gson = new Gson();

        BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        String data = input.readLine();
        JsonArray objects = gson.fromJson(data, JsonArray.class);
        System.out.println(objects);
        
        return objects;

    }
    
    
    public boolean repDades3(Socket socket) throws IOException {

        Gson gson = new Gson();

        BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        String data = input.readLine();
        System.out.println(" Data es :"+data );
        JsonObject objecte = gson.fromJson(data, JsonObject.class);
        Boolean correcte = objecte.get("correcte").getAsBoolean();
               
        return correcte;

    }
    
}
