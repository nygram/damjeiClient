/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

/**
 *
 * @author JavierFernándezDíaz
 */


public class ConsultesEmpleats {
    
    public static final int LOGIN = 0;
    public static final int INSERTAR = 1;
    public static final int ACTUALITZAR = 2;
    public static final int ELIMINAR = 3;
    public static final int LLISTAR = 4;
    
    
    int token = 0;    
    int port = 567;
    String ip = "127.0.0.1";

    
    public void enviaLogin(Empleats empleat) throws IOException{
            
            Socket socket = new Socket("ip", port);
            
            Gson gson = new Gson();
            
            JsonObject obtEmpleat = new JsonObject();
            obtEmpleat.add("empleat", gson.toJsonTree(empleat));
            obtEmpleat.addProperty("accio", LOGIN);
            
            
            String json = gson.toJson(obtEmpleat);
            System.out.println(json);
            
            OutputStream sortida = socket.getOutputStream();
            sortida.write(json.getBytes());
            
            
            
            
            
    
}
    
}
