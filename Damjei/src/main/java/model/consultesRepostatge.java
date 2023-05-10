package model;

import com.comDades;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.net.Socket;
import static model.consultesCombustible.LLISTAR;
import vista.frmCombustible;
import vista.frmRepostatge;

/**
 *
 * @author Javi
 */
public class consultesRepostatge {

    private frmRepostatge vista;
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

    public void carregaComboVehicle(frmRepostatge vista, String token) throws IOException {

        Socket socket = new Socket(ip, port);
        comDades com = new comDades();
        System.out.println("token " + token);
        Vehicle ve = new Vehicle();

        Gson gson = new Gson();
        
        JsonObject obtVehiculo = new JsonObject();
        obtVehiculo.add("vehicle", gson.toJsonTree(ve));
        obtVehiculo.addProperty("accio", LLISTAR);
        obtVehiculo.addProperty("token", token);
        obtVehiculo.addProperty("clase", "Vehicle.class");

        com.enviaDades(obtVehiculo, socket);
        
        JsonArray vehicle = com.repDades4(socket);
        for (JsonElement vehicles : vehicle) {
            JsonObject veh = vehicles.getAsJsonObject();
            //int id = veh.get("idvehiculo").getAsInt();
            //String modelo = veh.get("modelo").getAsString();
           String matricula = veh.get("matricula").getAsString();
            
            vista.cmbVehicles.addItem(matricula);
            
        }
       
    }
}
