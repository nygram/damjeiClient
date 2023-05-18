package model;

import com.comDades;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import controlador.ctrlLogin;
import controlador.ctrlRepostar;
import java.awt.Color;
import java.io.IOException;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
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

    public JsonArray vehicleCombo(frmRepostatge vista, String token) throws IOException {

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
        System.out.println("Vehicle es "+vehicle);

        return vehicle;
    }
    
    public JsonArray combustibleCombo(frmRepostatge vista, String token) throws IOException {

        Socket socket = new Socket(ip, port);
        comDades com = new comDades();
        System.out.println("token " + token);
        Combustible combus = new Combustible();

        Gson gson = new Gson();

        JsonObject obtCombustible = new JsonObject();
        obtCombustible.add("combustible", gson.toJsonTree(combus));
        obtCombustible.addProperty("accio", LLISTAR);
        obtCombustible.addProperty("token", token);
        obtCombustible.addProperty("clase", "Combustible.class");

        com.enviaDades(obtCombustible, socket);

        JsonArray combustible = com.repDades4(socket);
        System.out.println("Vehicle es "+combustible);

        return combustible;
    }

    public Vector<Vehicle> mostrarVehicles(String token) {
        JsonArray vehicle;
        Vector<Vehicle> vectorVehicles = new Vector<Vehicle>();
        Vehicle vehi;

        try {
            vehi = new Vehicle();
            vehi.setIdvehiculo(0);
            vehi.setMatricula("Seleccioni un vehicle");
            vectorVehicles.add(vehi);

            vehicle = vehicleCombo(vista, token);

            for (JsonElement vehicles : vehicle) {
                JsonObject veh = vehicles.getAsJsonObject();
                vehi = new Vehicle();
                vehi.setIdvehiculo(Integer.parseInt(veh.get("idvehiculo").getAsString()));
                vehi.setMatricula(veh.get("matricula").getAsString());
                vehi.setKilometros_actuales(veh.get("kilometros_actuales").getAsFloat());
                vehi.setConductorid(veh.get("conductorid").getAsInt());
                vehi.setModelo(veh.get("modelo").getAsString());
                
                System.out.println("Object es :"+veh);
                vectorVehicles.add(vehi);
                
            }  
            
        } catch (IOException ex) {
            Logger.getLogger(ctrlLogin.class.getName()).log(Level.SEVERE, null, ex);
        }
        return vectorVehicles;

    }
    
    public Vector<Combustible> mostrarCombustible(String token) {
        JsonArray combustible;
        Vector<Combustible> vectorCombustible = new Vector<Combustible>();
        Combustible combu;

        try {
            combu = new Combustible();
            combu.setIdcombustible(0);
            combu.setNombre("Seleccioni un combustible");
            vectorCombustible.add(combu);

            combustible = combustibleCombo(vista, token);

            for (JsonElement vehicles : combustible) {
                JsonObject com = vehicles.getAsJsonObject();
                combu = new Combustible();
                combu.setIdcombustible(Integer.parseInt(com.get("idcombustible").getAsString()));
                combu.setNombre(com.get("nombre").getAsString());
                               
                System.out.println("Object es :"+combu);
                vectorCombustible.add(combu);
                
            }  
            
        } catch (IOException ex) {
            Logger.getLogger(ctrlLogin.class.getName()).log(Level.SEVERE, null, ex);
        }
        return vectorCombustible;

    }
     public boolean insertarRepostatge(Repostar repostar, String token) throws IOException {

        Gson gson = new Gson();
        Socket socket = new Socket(ip, port);
        frmRepostatge vista = new frmRepostatge();
        
        /**
         * Generem objecte Json amb l'objecte empleat i les propietats que hi volem
         * afegir (accio, token i classe). 
         */
        
        JsonObject obtRepostatge = new JsonObject();
        obtRepostatge.add("repostar", gson.toJsonTree(repostar));
        obtRepostatge.addProperty("accio", INSERTAR);
        obtRepostatge.addProperty("token", token);
        obtRepostatge.addProperty("clase", "Repostar.class");
        
        /**
         * Rebem un booleà que ens indica si s'ha fet correctament. 
         */
        
        com.enviaDades(obtRepostatge, socket);
        Boolean resposta = com.repDades3(socket);
        System.out.println("La resposta es " + resposta);
        return resposta;

    }
     
     public void carregaTaula(frmRepostatge vista, String token) throws IOException {

        this.vista = vista;
        this.token = token;

        DefaultTableModel modeloTabla = new DefaultTableModel() {
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return false;
            }
        };
        vista.taulaRespostatge.setModel(modeloTabla);
        vista.taulaRespostatge.setRowSorter(new TableRowSorter<DefaultTableModel>(modeloTabla));
        vista.taulaRespostatge.setAutoCreateRowSorter(true);
        vista.taulaRespostatge.setBackground(Color.WHITE);
        vista.taulaRespostatge.setSelectionBackground(new Color(250, 201, 104));
        vista.taulaRespostatge.setOpaque(true);

        //vista.btnModificar.setVisible(false);
        
        
        /**
         * Inicialitzazem Socket i comDades, encarregats de la cominiació
         * amb el servidor
         */

        Socket socket = new Socket(ip, port);
        comDades com = new comDades();
        System.out.println("token " + token);
        Repostar re = new Repostar();

        Gson gson = new Gson();
        
         /**
         * Generem objecte Json amb l'objecte empleat i les propietats que hi volem
         * afegir (accio, token i classe).
         */

        JsonObject obtRepostatge = new JsonObject();
        obtRepostatge.add("repostar", gson.toJsonTree(re));
        obtRepostatge.addProperty("accio", LLISTAR);
        obtRepostatge.addProperty("token", token);
        obtRepostatge.addProperty("clase", "Repostar.class");

        com.enviaDades(obtRepostatge, socket);

        modeloTabla.addColumn("Matricula");
        modeloTabla.addColumn("Model");
        modeloTabla.addColumn("Data");
        modeloTabla.addColumn("Import");
        
        
        
        /**
         * Rebem les dades com a array de Objects. Fent recorrgut per l'array, 
         * recollim les dades i les afegim a les files de la taula
         */

        JsonArray repostatge = com.repDades4(socket);
        for (JsonElement repostatges : repostatge) {

            JsonObject repo = repostatges.getAsJsonObject();
            int idvehiculo = repo.get("vehiculoid").getAsInt();
            int idconductor = repo.get("conductorid").getAsInt();
            String dat = repo.get("fecha_repostar").getAsString();
            Float precio = repo.get("importe_repostar").getAsFloat();
            

            Object[] fila = {idvehiculo, idconductor, dat, precio};
            modeloTabla.addRow(fila);
        }

    }
}
