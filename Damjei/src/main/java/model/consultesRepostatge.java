package model;

import com.SocketSSL_Conexio;
import com.comDades;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import controlador.ctrlLogin;
import controlador.ctrlRepostar;
import java.awt.Color;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.Socket;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
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

    public JsonArray vehicleCombo(frmRepostatge vista, String token) throws IOException, KeyStoreException, FileNotFoundException, CertificateException, UnrecoverableKeyException, KeyManagementException, NoSuchAlgorithmException {

        SocketSSL_Conexio conexioSSL = new SocketSSL_Conexio();
        Socket socket = conexioSSL.connect(ip, port);
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
        System.out.println("Vehicle es " + vehicle);

        return vehicle;
    }

    public JsonArray combustibleCombo(frmRepostatge vista, String token) throws IOException, KeyStoreException, FileNotFoundException, CertificateException, UnrecoverableKeyException, KeyManagementException, NoSuchAlgorithmException {

        SocketSSL_Conexio conexioSSL = new SocketSSL_Conexio();
        Socket socket = conexioSSL.connect(ip, port);
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
        System.out.println("Vehicle es " + combustible);

        return combustible;
    }

    public Vector<Vehicle> mostrarVehicles(String token) throws IOException, KeyStoreException, FileNotFoundException, CertificateException, UnrecoverableKeyException, KeyManagementException, NoSuchAlgorithmException {
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

                System.out.println("Object es :" + veh);
                vectorVehicles.add(vehi);

            }

        } catch (IOException ex) {
            Logger.getLogger(ctrlLogin.class.getName()).log(Level.SEVERE, null, ex);
        }
        return vectorVehicles;

    }

    public Vector<Combustible> mostrarCombustible(String token) throws IOException, KeyStoreException, FileNotFoundException, CertificateException, UnrecoverableKeyException, KeyManagementException, NoSuchAlgorithmException {
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

                System.out.println("Object es :" + combu);
                vectorCombustible.add(combu);

            }

        } catch (IOException ex) {
            Logger.getLogger(ctrlLogin.class.getName()).log(Level.SEVERE, null, ex);
        }
        return vectorCombustible;

    }

    public boolean insertarRepostatge(Repostar repostar, String token) throws IOException, KeyStoreException, FileNotFoundException, CertificateException, UnrecoverableKeyException, KeyManagementException, NoSuchAlgorithmException {

        Gson gson = new Gson();
        SocketSSL_Conexio conexioSSL = new SocketSSL_Conexio();
        Socket socket = conexioSSL.connect(ip, port);
        frmRepostatge vista = new frmRepostatge();
        Boolean correcte = null;

        /**
         * Generem objecte Json amb l'objecte empleat i les propietats que hi
         * volem afegir (accio, token i classe).
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
        JsonObject respon = com.repDades(socket);
        correcte = respon.get("correcte").getAsBoolean();
        String aviso = respon.get("aviso").getAsString();
        if (aviso == null){
            aviso = "No te revisions pendents";
        }
        Boolean estado_revision = respon.get("estado_revision").getAsBoolean();
        JOptionPane.showMessageDialog(null, aviso);

        return correcte;
    }

    public void carregaTaula(frmRepostatge vista, String token) throws IOException, KeyStoreException, FileNotFoundException, CertificateException, UnrecoverableKeyException, KeyManagementException, NoSuchAlgorithmException {

        this.vista = vista;
        this.token = token;
        int i = 0;
        DemanaDades dades = new DemanaDades();

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
         * Inicialitzazem Socket i comDades, encarregats de la cominiació amb el
         * servidor
         */
        SocketSSL_Conexio conexioSSL = new SocketSSL_Conexio();
        Socket socket = conexioSSL.connect(ip, port);
        comDades com = new comDades();
        System.out.println("token " + token);
        Repostar re = new Repostar();

        Gson gson = new Gson();

        /**
         * Generem objecte Json amb l'objecte empleat i les propietats que hi
         * volem afegir (accio, token i classe).
         */
        JsonObject obtRepostatge = new JsonObject();
        obtRepostatge.add("repostar", gson.toJsonTree(re));
        obtRepostatge.addProperty("accio", LLISTAR);
        obtRepostatge.addProperty("token", token);
        obtRepostatge.addProperty("clase", "Repostar.class");

        com.enviaDades(obtRepostatge, socket);

        modeloTabla.addColumn("Id");
        modeloTabla.addColumn("Conductor");
        modeloTabla.addColumn("Matricula");
        modeloTabla.addColumn("Vehicle");
        modeloTabla.addColumn("Data");
        modeloTabla.addColumn("Import");

        /**
         * Rebem les dades com a array de Objects. Fent recorrgut per l'array,
         * recollim les dades i les afegim a les files de la taula
         */
        JsonArray repostatge = com.repDades4(socket);
        for (JsonElement repostatges : repostatge) {

            JsonObject repo = repostatges.getAsJsonObject();
            int idrepostatge = repo.get("idrepostar").getAsInt();
            int idvehiculo = repo.get("vehiculoid").getAsInt();
            String mat = dades.nomVehicle(idvehiculo, token);
            int idconductor = repo.get("conductorid").getAsInt();
            String nomvehicle = dades.nomModelVehicle(idvehiculo, token);
            String dat = repo.get("fecha_repostar").getAsString();
            Float precio = repo.get("importe_repostar").getAsFloat();
            
            Object[] fila = {idrepostatge, idconductor, mat, nomvehicle, dat, precio};
            modeloTabla.addRow(fila);
            
        }

    }
    public boolean eliminarRepostatge(Repostar repostatge, String token) throws IOException, KeyStoreException, FileNotFoundException, CertificateException, UnrecoverableKeyException, KeyManagementException, NoSuchAlgorithmException {

        Gson gson = new Gson();
        SocketSSL_Conexio conexioSSL = new SocketSSL_Conexio();
        Socket socket = conexioSSL.connect(ip, port);

        /**
         * Generem objecte Json amb l'objecte empleat i les propietats que hi
         * volem afegir (accio, token i classe).
         */
        JsonObject obtRepostatge = new JsonObject();
        obtRepostatge.add("repostar", gson.toJsonTree(repostatge));
        obtRepostatge.addProperty("accio", ELIMINAR);
        obtRepostatge.addProperty("token", token);
        obtRepostatge.addProperty("clase", "Repostar.class");

        /**
         * Rebem un booleà que ens indica si s'ha fet correctament.
         */
        com.enviaDades(obtRepostatge, socket);
        Boolean resposta = com.repDades3(socket);

        return resposta;
    }
}
