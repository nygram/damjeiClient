/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import com.SocketSSL_Conexio;
import com.comDades;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import controlador.ctrlLogin;
import java.awt.List;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.Socket;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import static model.consultasManteniment.LISTARID;
import static model.consultasVehicle.LLISTAR;
import static model.consultesEmpleats.LISTARID;
import static model.consultesEmpleats.LLISTAR;
import static org.apache.maven.wagon.PathUtils.port;
import vista.frmVehicle;

/**
 *
 * @author Javi
 */
public class DemanaDades {

    int idconductor;
    int empleadoid;
    String nomEmpleat;
    

    int mantenimientoid;
    String nomManteniment;

    int vehiculoid;
    String matriculaVeh;
    Float kmActuals;

    public static final int LISTARID = 7;
    public static final int LLISTAR = 5;
    int port = 8180;
    String ip = "127.0.0.1";
    SocketSSL_Conexio conexioSSL = new SocketSSL_Conexio();

    public int idEmpleat(int idconductor, String token) throws KeyStoreException, IOException, FileNotFoundException, CertificateException, NoSuchAlgorithmException, KeyManagementException, UnrecoverableKeyException {

        this.idconductor = idconductor;

        Empleats emp = new Empleats();

        Socket socket = conexioSSL.connect(ip, port);
        comDades com = new comDades();
        Conductor conductor = new Conductor();
        conductor.setIdconductor(idconductor);

        Gson gson = new Gson();
        System.out.println("dintre");

        /**
         * Generem objecte Json amb l'objecte empleat i les propietats que hi
         * volem afegir (accio, token i classe).
         */
        JsonObject obtConductor = new JsonObject();
        obtConductor.add("conductor", gson.toJsonTree(conductor));
        obtConductor.addProperty("accio", LISTARID);
        obtConductor.addProperty("token", token);
        obtConductor.addProperty("clase", "Conductor.class");
        System.out.println("ebviadades");

        com.enviaDades(obtConductor, socket);

        JsonArray conduc = com.repDades4(socket);
        System.out.println("repdades");
        for (JsonElement conducs : conduc) {

            JsonObject conductors = conducs.getAsJsonObject();
            empleadoid = conductors.get("empleadoid").getAsInt();
            System.out.println("empleadoid es "+ empleadoid);
            
        }
        return empleadoid;
        
    }
    
    public String nomEmpleat (int idempleado, String token) throws KeyStoreException, IOException, FileNotFoundException, CertificateException, UnrecoverableKeyException, KeyManagementException, NoSuchAlgorithmException{
        
        this.empleadoid = idempleado;
        Empleats emp = new Empleats();
        Gson gson = new Gson();
        
        Socket socket = conexioSSL.connect(ip, port);
        comDades com = new comDades();
        
        emp.setIdempleado(empleadoid);

        JsonObject obtEmpleat = new JsonObject();
        obtEmpleat.add("empleat", gson.toJsonTree(emp));
        obtEmpleat.addProperty("accio", LISTARID);
        obtEmpleat.addProperty("token", token);
        obtEmpleat.addProperty("clase", "Empleats.class");
        System.out.println("enviadades2");
        com.enviaDades(obtEmpleat, socket);
        
        
        Object[] empleat = com.repDades2(socket);
        System.out.println("repdades2");
        for (Object empleats : empleat) {
            Empleats emp2 = gson.fromJson(empleats.toString(), Empleats.class);
            
            String nombre = emp2.getNom();
            String apellido = emp2.getApellidos();
            nomEmpleat = nombre+ " " +apellido;
        }

        socket.close();
        return nomEmpleat;

    }
    public String dniEmpleat (int idempleado, String token) throws KeyStoreException, IOException, FileNotFoundException, CertificateException, UnrecoverableKeyException, KeyManagementException, NoSuchAlgorithmException{
        
        this.empleadoid = idempleado;
        Empleats emp = new Empleats();
        Gson gson = new Gson();
        String dniEmpleat = "";
        
        Socket socket = conexioSSL.connect(ip, port);
        comDades com = new comDades();
        
        emp.setIdempleado(empleadoid);

        JsonObject obtEmpleat = new JsonObject();
        obtEmpleat.add("empleat", gson.toJsonTree(emp));
        obtEmpleat.addProperty("accio", LISTARID);
        obtEmpleat.addProperty("token", token);
        obtEmpleat.addProperty("clase", "Empleats.class");
        System.out.println("enviadades2");
        com.enviaDades(obtEmpleat, socket);
        
        
        Object[] empleat = com.repDades2(socket);
        System.out.println("repdades2");
        for (Object empleats : empleat) {
            Empleats emp2 = gson.fromJson(empleats.toString(), Empleats.class);
            
            dniEmpleat = emp2.getDni();
            
        }

        socket.close();
        return dniEmpleat;

    }

    public String nomManteniment(int mantenimientoid, String token) throws KeyStoreException, IOException, FileNotFoundException, CertificateException, NoSuchAlgorithmException, KeyManagementException, UnrecoverableKeyException {

        this.mantenimientoid = mantenimientoid;

        Socket socket = conexioSSL.connect(ip, port);
        comDades com = new comDades();
        Mantenimiento man = new Mantenimiento();
        man.setIdmantenimiento(mantenimientoid);

        Gson gson = new Gson();

        /**
         * Generem objecte Json amb l'objecte empleat i les propietats que hi
         * volem afegir (accio, token i classe).
         */
        JsonObject obtManteniment = new JsonObject();
        obtManteniment.add("mantenimiento", gson.toJsonTree(man));
        obtManteniment.addProperty("accio", LISTARID);
        obtManteniment.addProperty("token", token);
        obtManteniment.addProperty("clase", "Mantenimiento.class");

        com.enviaDades(obtManteniment, socket);

        /**
         * Rebem les dades com a JsonArray. Fent recorregut per l'array,
         * recollim les dades i les afegim als textBox de la vista
         */
        JsonArray mantenimiento = com.repDades4(socket);
        for (JsonElement mantenimientos : mantenimiento) {

            JsonObject manteniments = mantenimientos.getAsJsonObject();

            nomManteniment = manteniments.get("nombre").getAsString();

        }
        socket.close();
        return nomManteniment;

    }

    public String nomVehicle(int vehiculoid, String token) throws KeyStoreException, IOException, FileNotFoundException, CertificateException, NoSuchAlgorithmException, KeyManagementException, UnrecoverableKeyException {

        this.vehiculoid = vehiculoid;

        Socket socket = conexioSSL.connect(ip, port);
        comDades com = new comDades();
        Vehicle veh = new Vehicle();
        veh.setIdvehiculo(vehiculoid);

        Gson gson = new Gson();

        /**
         * Generem objecte Json amb l'objecte empleat i les propietats que hi
         * volem afegir (accio, token i classe).
         */
        JsonObject obtVehiculo = new JsonObject();
        obtVehiculo.add("vehicle", gson.toJsonTree(veh));
        obtVehiculo.addProperty("accio", LISTARID);
        obtVehiculo.addProperty("token", token);
        obtVehiculo.addProperty("clase", "Vehicle.class");

        com.enviaDades(obtVehiculo, socket);

        /**
         * Rebem les dades com a JsonArray. Fent recorregut per l'array,
         * recollim les dades i les afegim als textBox de la vista
         */
        JsonArray vehiculo = com.repDades4(socket);
        for (JsonElement vehiculos : vehiculo) {

            JsonObject vehicles = vehiculos.getAsJsonObject();

            matriculaVeh = vehicles.get("matricula").getAsString();

        }
        socket.close();
        return matriculaVeh;
    }

    public Float kmActuals(int vehiculoid, String token) throws KeyStoreException, IOException, FileNotFoundException, CertificateException, NoSuchAlgorithmException, KeyManagementException, UnrecoverableKeyException {

        this.vehiculoid = vehiculoid;

        Socket socket = conexioSSL.connect(ip, port);
        comDades com = new comDades();
        Vehicle veh = new Vehicle();
        veh.setIdvehiculo(vehiculoid);

        Gson gson = new Gson();

        /**
         * Generem objecte Json amb l'objecte empleat i les propietats que hi
         * volem afegir (accio, token i classe).
         */
        JsonObject obtVehiculo = new JsonObject();
        obtVehiculo.add("vehicle", gson.toJsonTree(veh));
        obtVehiculo.addProperty("accio", LISTARID);
        obtVehiculo.addProperty("token", token);
        obtVehiculo.addProperty("clase", "Vehicle.class");

        com.enviaDades(obtVehiculo, socket);

        /**
         * Rebem les dades com a JsonArray. Fent recorregut per l'array,
         * recollim les dades i les afegim als textBox de la vista
         */
        JsonArray vehiculo = com.repDades4(socket);
        for (JsonElement vehiculos : vehiculo) {

            JsonObject vehicles = vehiculos.getAsJsonObject();
            System.out.println("vehicles = " + vehicles);
            int id = vehicles.get("idvehiculo").getAsInt();
            if (id == vehiculoid) {
                kmActuals = vehicles.get("kilometros_actuales").getAsFloat();

            }

        }
        socket.close();
        return kmActuals;
    }

    public String nomModelVehicle(int vehiculoid, String token) throws KeyStoreException, IOException, FileNotFoundException, CertificateException, NoSuchAlgorithmException, KeyManagementException, UnrecoverableKeyException {

        this.vehiculoid = vehiculoid;
        String nomModel = null;
        

        Socket socket = conexioSSL.connect(ip, port);
        comDades com = new comDades();
        Vehicle veh = new Vehicle();
        veh.setIdvehiculo(vehiculoid);

        Gson gson = new Gson();

        /**
         * Generem objecte Json amb l'objecte empleat i les propietats que hi
         * volem afegir (accio, token i classe).
         */
        JsonObject obtVehiculo = new JsonObject();
        obtVehiculo.add("vehicle", gson.toJsonTree(veh));
        obtVehiculo.addProperty("accio", LISTARID);
        obtVehiculo.addProperty("token", token);
        obtVehiculo.addProperty("clase", "Vehicle.class");

        com.enviaDades(obtVehiculo, socket);

        /**
         * Rebem les dades com a JsonArray. Fent recorregut per l'array,
         * recollim les dades i les afegim als textBox de la vista
         */
        JsonArray vehiculo = com.repDades4(socket);
        for (JsonElement vehiculos : vehiculo) {

            JsonObject vehicles = vehiculos.getAsJsonObject();

            String marca = vehicles.get("marca").getAsString();
            String model = vehicles.get("modelo").getAsString();
            nomModel = marca + " " + model;
        }
        socket.close();
        return nomModel;
    }

    public ArrayList<String> data_carnet(int idempleado, String token) throws IOException, KeyStoreException, FileNotFoundException, CertificateException, UnrecoverableKeyException, KeyManagementException, NoSuchAlgorithmException {

        ArrayList<String> datescarnet = new ArrayList<>(2);

        Socket socket = conexioSSL.connect(ip, port);
        comDades com = new comDades();
        Conductor con = new Conductor();

        Gson gson = new Gson();

        /**
         * Generem objecte Json amb l'objecte empleat i les propietats que hi
         * volem afegir (accio, token i classe).
         */
        JsonObject obtConductor = new JsonObject();
        obtConductor.add("conductor", gson.toJsonTree(con));
        obtConductor.addProperty("accio", LLISTAR);
        obtConductor.addProperty("token", token);
        obtConductor.addProperty("clase", "Conductor.class");

        com.enviaDades(obtConductor, socket);
        JsonArray conductor = com.repDades4(socket);
        for (JsonElement conductores : conductor) {

            JsonObject conduc = conductores.getAsJsonObject();

            int emp = conduc.get("empleadoid").getAsInt();
            if (emp == idempleado) {
                String datacarn = conduc.get("fecha_carnet").getAsString();
                String datacarncad = conduc.get("fecha_caducidad_carnet").getAsString();
                datescarnet.add(datacarn);
                datescarnet.add(datacarncad);
            }

        }
        socket.close();
        return datescarnet;

    }
    
    public int tornaIdConductor (int idEmpleat, String token) throws KeyStoreException, IOException, FileNotFoundException, CertificateException, UnrecoverableKeyException, KeyManagementException, NoSuchAlgorithmException{
        
        this.empleadoid = idEmpleat;
        Conductor conductor = new Conductor();
        int idconductor = 0;
        int idempleado = 0;
        
        Socket socket = conexioSSL.connect(ip, port);
        comDades com = new comDades();
        
        Gson gson = new Gson();
        
        JsonObject obtConductor = new JsonObject();
        obtConductor.add("conductor", gson.toJsonTree(conductor));
        obtConductor.addProperty("accio", LLISTAR);
        obtConductor.addProperty("token", token);
        obtConductor.addProperty("clase", "Conductor.class");
        
        com.enviaDades(obtConductor, socket);
        
        JsonArray conduc = com.repDades4(socket);
        System.out.println("rep dades");
        
        for (JsonElement conducs : conduc) {

            JsonObject conductors = conducs.getAsJsonObject();
            
            int idemp = conductors.get("empleadoid").getAsInt();
            if (idemp == empleadoid ){
                idconductor = conductors.get("idconductor").getAsInt();
            }
            
        }
        return idconductor;

        /**
         * Rebem les dades com a JsonArray. Fent recorregut per l'array,
         * recollim les dades i les afegim als textBox de la vista
         */
        
        
    }
    
   

}
