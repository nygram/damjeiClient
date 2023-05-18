/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Utils;

import com.comDades;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.net.Socket;
import model.Empleats;
import model.Repostar;
import static model.consultesRepostatge.LLISTAR;

/**
 *
 * @author JavierFernándezDíaz
 */
public class DonaCamp {

    int idconductor;
    String nomempleat;
    comDades com = new comDades();
    private Socket socket;
    private String token;
    public static final int LLISTAR = 5;
    public static final int INSERTAR = 2;
    public static final int ELIMINAR = 4;
    public static final int MODIFICAR = 6;
    public static final int LISTARID = 7;
    int port = 8180;
    String ip = "127.0.0.1";

    public int getIdconductor() {
        return idconductor;
    }

    public void setIdconductor(int idconductor) {
        this.idconductor = idconductor;
    }

    public String getNomempleat() {
        return nomempleat;
    }

    public void setNomempleat(String nomempleat) {
        this.nomempleat = nomempleat;
    }
    

    public String nomEmpleat(int idconductor) throws IOException {
        
        this.idconductor = idconductor;
        
        Socket socket = new Socket(ip, port);
        comDades com = new comDades();
        System.out.println("token " + token);
        Repostar re = new Repostar();

        Gson gson = new Gson();
        
         /**
         * Generem objecte Json amb l'objecte empleat i les propietats que hi volem
         * afegir (accio, token i classe).
         */

        JsonObject obtConductor = new JsonObject();
        obtConductor.add("repostar", gson.toJsonTree(re));
        obtConductor.addProperty("accio", LISTARID);
        obtConductor.addProperty("token", token);
        obtConductor.addProperty("clase", "Conductor.class");
        
         com.enviaDades(obtConductor, socket);
         
         Object[] empleat = com.repDades2(socket);
        for (Object empleats : empleat) {
            Conductor con = gson.fromJson(empleats.toString(), Conductor.class);

    }

}
