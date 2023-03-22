/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import com.comDades;
import model.Empleats;

/**
 *
 * @author JavierFernándezDíaz
 */
public class Comunica {

    public static final int LOGIN = 0;
    public static final int INSERTAR = 1;
    public static final int ACTUALITZAR = 2;
    public static final int ELIMINAR = 3;
    public static final int LLISTAR = 4;

    int token = 0;
    int port = 10000;
    String ip = "127.0.0.1";

    public ArrayList enviaLogin(Empleats empleat) throws IOException {

        ArrayList<Object> List = new ArrayList<Object>();
        Socket socket = new Socket(ip, port);
        comDades com = new comDades();

        Gson gson = new Gson();

        JsonObject obtEmpleat = new JsonObject();
        obtEmpleat.add("empleat", gson.toJsonTree(empleat));
        obtEmpleat.addProperty("accio", ELIMINAR);
        obtEmpleat.addProperty("clase", "Empleats.class");
        
        
        com.enviaDades(obtEmpleat, socket);
        JsonObject objecte = com.repDades(socket);
       
        boolean correcte = objecte.get("correcte").getAsBoolean();
        boolean administrador = objecte.get("administrador").getAsBoolean();
        int token = objecte.get("token").getAsInt();
        
        List.add(correcte);
        List.add(administrador);
        List.add(token);

        return List;

    }

    

}
