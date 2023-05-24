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
import java.awt.Color;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.Socket;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import vista.frmRevisions;

/**
 *
 * @author Javi
 */
public class consultasRevisiones {
    private frmRevisions vista;
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
    DemanaDades dades = new DemanaDades();

    public consultasRevisiones() {
    }
    
    public void carregaTaula(frmRevisions vista, String token) throws IOException, KeyStoreException, FileNotFoundException, CertificateException, UnrecoverableKeyException, KeyManagementException, NoSuchAlgorithmException{

        this.vista = vista;
        this.token = token;
        

        DefaultTableModel modeloTabla = new DefaultTableModel() {
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return false;
            }
        };
        vista.taulaRevisions.setModel(modeloTabla);
        vista.taulaRevisions.setRowSorter(new TableRowSorter<DefaultTableModel>(modeloTabla));
        vista.taulaRevisions.setAutoCreateRowSorter(true);
        vista.taulaRevisions.setBackground(Color.WHITE);
        vista.taulaRevisions.setSelectionBackground(new Color(250, 201, 104));
        vista.taulaRevisions.setOpaque(true);

        vista.btnModificar.setVisible(false);
        vista.jTabbedPane1.setSelectedIndex(0);
        
        /**
         * Inicialitzazem Socket i comDades, encarregats de la cominiació
         * amb el servidor
         */

        SocketSSL_Conexio conexioSSL = new SocketSSL_Conexio();
        Socket socket = conexioSSL.connect(ip, port);
        comDades com = new comDades();
        System.out.println("token " + token);
        Revisiones re = new Revisiones();

        Gson gson = new Gson();
        
         /**
         * Generem objecte Json amb l'objecte empleat i les propietats que hi volem
         * afegir (accio, token i classe).
         */

        JsonObject obtRevisio = new JsonObject();
        obtRevisio.add("revision", gson.toJsonTree(re));
        obtRevisio.addProperty("accio", LLISTAR);
        obtRevisio.addProperty("token", token);
        obtRevisio.addProperty("clase", "Revisiones.class");

        com.enviaDades(obtRevisio, socket);

        modeloTabla.addColumn("Id");
        modeloTabla.addColumn("Vehiculo");
        modeloTabla.addColumn("Mantenimiento");
        modeloTabla.addColumn("Estado revision");
        modeloTabla.addColumn("Km");
        
        /**
         * Rebem les dades com a array de Objects. Fent recorrgut per l'array, 
         * recollim les dades i les afegim a les files de la taula
         */

        JsonArray revisio = com.repDades4(socket);
        System.out.println("revisio recibida es :"+revisio);
        for (JsonElement revisions : revisio) {

            JsonObject revi = revisions.getAsJsonObject();
            int idrevision = revi.get("idrevision").getAsInt();
            int idvehiculo = revi.get("vehiculoid").getAsInt();
            String matriculaVeh = dades.nomVehicle(idvehiculo, token);
            int idmantenimiento = revi.get("mantenimientoid").getAsInt();
            String mantenimiento = dades.nomManteniment(idmantenimiento, token);
            Boolean estado = revi.get("estado_revision").getAsBoolean();
            Float km = revi.get("kilometros_revision").getAsFloat();

            Object[] fila = {idrevision, matriculaVeh, mantenimiento, estado, km};
            modeloTabla.addRow(fila);
        }

    }
    public void carregaRevisio(int codigo, frmRevisions vista) throws IOException, KeyStoreException, FileNotFoundException, CertificateException, UnrecoverableKeyException, KeyManagementException, NoSuchAlgorithmException {

        this.vista = vista;
        this.token = token;
        
        /**
         * Inicialitzazem Socket i comDades, encarregats de la comuniació
         * amb el servidor
         */

        SocketSSL_Conexio conexioSSL = new SocketSSL_Conexio();
        Socket socket = conexioSSL.connect(ip, port);
        comDades com = new comDades();
        Revisiones rev = new Revisiones();
        rev.setIdrevision(codigo);
        

        Gson gson = new Gson();
        
        /**
         * Generem objecte Json amb l'objecte combustible i les propietats que hi volem
         * afegir (accio, token i classe). 
         */

        JsonObject obtRevisio = new JsonObject();
        obtRevisio.add("revision", gson.toJsonTree(rev));
        obtRevisio.addProperty("accio", LISTARID);
        obtRevisio.addProperty("token", token);
        obtRevisio.addProperty("clase", "Revisiones.class");

        com.enviaDades(obtRevisio, socket);
        
        /**
         * Rebem les dades com a JsonArray. Fent recorregut per l'array, 
         * recollim les dades i les afegim als textBox de la vista
         */

        JsonArray revisio = com.repDades4(socket);
        for (JsonElement revisions : revisio) {

            JsonObject revi = revisions.getAsJsonObject();
            int idvehiculo = revi.get("vehiculoid").getAsInt();
            String matriculaVeh = dades.nomVehicle(idvehiculo, token);
            int idmantenimiento = revi.get("mantenimientoid").getAsInt();
            String mantenimiento = dades.nomManteniment(idmantenimiento, token);
            String fecha = revi.get("fecha_revision").getAsString();
            if (fecha == null){
                fecha = " ";
            }
            Float km = revi.get("kilometros_revision").getAsFloat();
            Boolean estado = revi.get("estado_revision").getAsBoolean();

            Revisiones revi2 = new Revisiones();
            revi2.setVehiculoid(idvehiculo);
            revi2.setMantenimientoid(idmantenimiento);
            revi2.setFecha_revision(fecha);
            revi2.setKilometros_revision(km);
            revi2.setEstado_revision(estado);

            vista.txtVehicle.setText(String.valueOf(matriculaVeh));
            vista.txtManteniment.setText(String.valueOf(mantenimiento));
            vista.txtData.setText(fecha);
            vista.txtKm.setText(String.valueOf(km));
            vista.rbtEstat.setSelected(estado);
            //vista.txtEstat.setText(String.valueOf(estado));
            vista.txtKmActuals.setText(dades.kmActuals(idvehiculo, token).toString());
        }

    }
    
    public boolean eliminarRevisio(Revisiones revisio, String token) throws IOException, KeyStoreException, FileNotFoundException, CertificateException, UnrecoverableKeyException, KeyManagementException, NoSuchAlgorithmException {

        
            Gson gson = new Gson();
            SocketSSL_Conexio conexioSSL = new SocketSSL_Conexio();
            Socket socket = conexioSSL.connect(ip, port);

            /**
             * Generem objecte Json amb l'objecte empleat i les propietats que
             * hi volem afegir (accio, token i classe).
             */
            JsonObject obtRevisio = new JsonObject();
            obtRevisio.add("revision", gson.toJsonTree(revisio));
            obtRevisio.addProperty("accio", ELIMINAR);
            obtRevisio.addProperty("token", token);
            obtRevisio.addProperty("clase", "Revisiones.class");

            /**
             * Rebem un booleà que ens indica si s'ha fet correctament.
             */
            com.enviaDades(obtRevisio, socket);
            Boolean resposta = com.repDades3(socket);

            return resposta;
             

        } 
    
    public boolean modificarRevisio(Revisiones revisio, String token) throws IOException, KeyStoreException, FileNotFoundException, CertificateException, UnrecoverableKeyException, KeyManagementException, NoSuchAlgorithmException {

        Gson gson = new Gson();
        SocketSSL_Conexio conexioSSL = new SocketSSL_Conexio();
        Socket socket = conexioSSL.connect(ip, port);
        frmRevisions vista = new frmRevisions();

        /**
         * Generem objecte Json amb l'objecte modificar i les propietats que hi
         * volem afegir (accio, token i classe).
         */
        JsonObject obtRevisio = new JsonObject();
        obtRevisio.add("revision", gson.toJsonTree(revisio));
        obtRevisio.addProperty("accio", MODIFICAR);
        obtRevisio.addProperty("token", token);
        obtRevisio.addProperty("clase", "Revisiones.class");

        /**
         * Rebem un booleà que ens indica si s'ha fet correctament.
         */
        com.enviaDades(obtRevisio, socket);
        Boolean resposta = com.repDades3(socket);
        return resposta;

    }
}
