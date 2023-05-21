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
import java.io.FileNotFoundException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import model.Empleats;

/**
 * Classe que s'encarrea de les comunicacions. Disposa de constants per
 * seleccionar la acció que es vol relitzar.
 *
 * @author JavierFernándezDíaz
 *
 */
public class Comunica {

    public static final int LOGIN = 0;
    public static final int LOGOUT = 1;
    public static final int INSERTAR = 2;
    public static final int ACTUALITZAR = 3;
    public static final int ELIMINAR = 4;
    public static final int LLISTAR = 5;

    int token = 0;
    int port = 8180;
    String ip = "127.0.0.1";

    /**
     * Getter
     *
     * @return token
     */
    public int getToken() {
        return token;
    }

    /**
     * Setter
     *
     * @param token
     */
    public void setToken(int token) {
        this.token = token;
    }

    /**
     * Mètode per fer Login al servidor. Rep un objecte empleat i l'encapsula en
     * un Objecte Json afegint com a propietats l'acció que volem que el
     * servidor faci i la classe a la que pertany
     *
     * @param empleat objecte de la classe Empleats
     * @return JsonObject que conté l'objecte empleat, l'acció que passem fent
     * servir les constants declarades: LOGIN = 0; LOGOUT = 1; INSERTAR = 2;
     * ACTUALITZAR = 3; ELIMINAR = 4; LLISTAR = 5; i la classe a la que pertany
     * empleat
     * @throws IOException
     */
    public JsonObject enviaLogin(Empleats empleat) throws IOException, KeyStoreException, FileNotFoundException, CertificateException, UnrecoverableKeyException, KeyManagementException, NoSuchAlgorithmException {

        
        SocketSSL_Conexio conexioSSL = new SocketSSL_Conexio();
        Socket socket = conexioSSL.connect(ip, port);

        //Socket socket = new Socket(ip, port);
        comDades com = new comDades();

        Gson gson = new Gson();

        JsonObject obtEmpleat = new JsonObject();
        obtEmpleat.add("empleat", gson.toJsonTree(empleat));
        obtEmpleat.addProperty("accio", LOGIN);
        obtEmpleat.addProperty("clase", "Empleats.class");

        com.enviaDades(obtEmpleat, socket);
        JsonObject objecte = com.repDades(socket);

        socket.close();

        return objecte;

    }

    /**
     * /**
     * Mètode per fer Logout al servidor. Rep un int que es el token de la
     * sessió i l'envia al servidor per comunicar que s'ha fet el Logout. Torna
     * un booleà que ens confirma si s'ha fet el Logout correctement
     *
     * @param token identifica la sessió oberta amb el servidor
     * @return boolean ens confirma si el Logout s'ha fet correctement
     * @throws IOException
     */

    public boolean enviaLogout(String token) throws IOException, KeyStoreException, FileNotFoundException, CertificateException, UnrecoverableKeyException, KeyManagementException, NoSuchAlgorithmException {

        SocketSSL_Conexio conexioSSL = new SocketSSL_Conexio();
        Socket socket = conexioSSL.connect(ip, port);
        comDades com = new comDades();
        Gson gson = new Gson();

        JsonObject obtLogout = new JsonObject();
        obtLogout.addProperty("accio", LOGOUT);
        obtLogout.addProperty("token", token);

        com.enviaDades(obtLogout, socket);

        socket.close();

        boolean correcte = true;
        return correcte;
    }

}
