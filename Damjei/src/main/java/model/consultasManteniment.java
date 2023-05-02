package model;

import com.comDades;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.awt.Color;
import java.io.IOException;
import java.net.Socket;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import vista.frmManteniment;

/**
 * classe que s'encarrega de recollir les dades enviades per el controlador
 * i, un cop formatades, enviarles al servidor amb l'acció que volem fer 
 * amb aquestes dades: llistar, insertar, eliminar, modificar, llistar un únic
 * empleat
 * Seguidament recull la resposta i la tracta segons convingui
 * @author JavierFernándezDíaz
 */
public class consultasManteniment {

    private frmManteniment vista;
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

    public consultasManteniment() {
    }
    
    /**
     * Mètode que s'encarrega d'emplenar la taula. Rep la vista i el token.
     * Crea el model de la taula. Crida al servidor per obtenir les dades per
     * emplenar la taula i emplena la taula amb aquestes dades.
     * @param vista frmManteniment
     * @param token necesari per comunicar amb el servidor
     * @throws IOException 
     */

    public void carregaTaula(frmManteniment vista, String token) throws IOException {

        this.vista = vista;
        this.token = token;

        DefaultTableModel modeloTabla = new DefaultTableModel() {
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return false;
            }
        };
        vista.taulaMantenimiento.setModel(modeloTabla);
        vista.taulaMantenimiento.setRowSorter(new TableRowSorter<DefaultTableModel>(modeloTabla));
        vista.taulaMantenimiento.setAutoCreateRowSorter(true);
        vista.taulaMantenimiento.setBackground(Color.WHITE);
        vista.taulaMantenimiento.setSelectionBackground(new Color(250, 201, 104));
        vista.taulaMantenimiento.setOpaque(true);

        vista.btnModificar.setVisible(false);
        vista.jTabbedPane1.setSelectedIndex(0);
        
        /**
         * Inicialitzazem Socket i comDades, encarregats de la cominiació
         * amb el servidor
         */

        Socket socket = new Socket(ip, port);
        comDades com = new comDades();
        System.out.println("token " + token);
        Mantenimiento man = new Mantenimiento();

        Gson gson = new Gson();
        
         /**
         * Generem objecte Json amb l'objecte empleat i les propietats que hi volem
         * afegir (accio, token i classe).
         */

        JsonObject obtMantenimiento = new JsonObject();
        obtMantenimiento.add("mantenimiento", gson.toJsonTree(man));
        obtMantenimiento.addProperty("accio", LLISTAR);
        obtMantenimiento.addProperty("token", token);
        obtMantenimiento.addProperty("clase", "Mantenimiento.class");

        com.enviaDades(obtMantenimiento, socket);

        modeloTabla.addColumn("Id");
        modeloTabla.addColumn("Nombre");
        modeloTabla.addColumn("Km mantenimiento");
        
        /**
         * Rebem les dades com a array de Objects. Fent recorrgut per l'array, 
         * recollim les dades i les afegim a les files de la taula
         */

        JsonArray mantenimiento = com.repDades4(socket);
        for (JsonElement mantenimientos : mantenimiento) {

            JsonObject manteniments = mantenimientos.getAsJsonObject();
            int id = manteniments.get("idmantenimiento").getAsInt();
            String nombre = manteniments.get("nombre").getAsString();
            Float km = null;
            if (manteniments.has("kilometros_mantenimiento")) {
                km = manteniments.get("kilometros_mantenimiento").getAsFloat();
            }

            Object[] fila = {id, nombre, km};
            modeloTabla.addRow(fila);
        }

    }
    
    /**
     * Mètode que s'encarrega de rebre les dades d'un manteniment concret per poder
     * emplenar els camps de la pestanya de detalls de empleat
     * @param codigo es la id que identifica a el manteniment
     * @param vista frmManteniments
     * @throws IOException 
     */

    public void carregaManteniment(int codigo, frmManteniment vista) throws IOException {

        this.vista = vista;
        this.token = token;
        
        /**
         * Inicialitzazem Socket i comDades, encarregats de la cominiació
         * amb el servidor
         */

        Socket socket = new Socket(ip, port);
        comDades com = new comDades();
        Mantenimiento man = new Mantenimiento();
        man.setIdmantenimiento(codigo);

        Gson gson = new Gson();
        
        /**
         * Generem objecte Json amb l'objecte empleat i les propietats que hi volem
         * afegir (accio, token i classe). 
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
            int id = manteniments.get("idmantenimiento").getAsInt();
            String nombre = manteniments.get("nombre").getAsString();
            /*Float km = null;
            if (manteniments.has("kilometros_mantenimiento")) {
                km = manteniments.get("kilometros_mantenimiento").getAsFloat();
            }
            //String fecha = manteniments.get("fecha_mantenimientos").getAsString();
            */

            Mantenimiento man2 = new Mantenimiento();
            man2.setIdmantenimiento(id);
            man2.setNombre(nombre);
            //man2.setKilometros_mantenimiento(km);
            //man2.setFecha_mantenimiento(fecha);

            vista.txtNom.setText(nombre);
            //vista.txtKm.setText(km.toString());
            //vista.txtFecha.setText(fecha);

        }

    }
    
    /**
     * Mètode que s'encarrega d'afegir un nou manteniment. 
     * @param manteniment el que volem afegir
     * @param token per poder parlar amb el server
     * @return
     * @throws IOException 
     */

    public boolean insertarManteniment(Mantenimiento manteniment, String token) throws IOException {

        Gson gson = new Gson();
        Socket socket = new Socket(ip, port);
        frmManteniment vista = new frmManteniment();
        
        /**
         * Generem objecte Json amb l'objecte empleat i les propietats que hi volem
         * afegir (accio, token i classe). 
         */
        
        JsonObject obtManteniment = new JsonObject();
        obtManteniment.add("mantenimiento", gson.toJsonTree(manteniment));
        obtManteniment.addProperty("accio", INSERTAR);
        obtManteniment.addProperty("token", token);
        obtManteniment.addProperty("clase", "Mantenimiento.class");
        
        /**
         * Rebem un booleà que ens indica si s'ha fet correctament. 
         */
        
        com.enviaDades(obtManteniment, socket);
        Boolean resposta = com.repDades3(socket);
        System.out.println("La resposta es " + resposta);
        return resposta;

    }
    
     /**
     * Mètode que s'encarrega de modificar un manteniment existent
     * @param manteniment el que volem modificar
     * @param token per poder parlar amb el server
     * @return
     * @throws IOException 
     */
    
    public boolean modificarManteniment(Mantenimiento manteniment, String token) throws IOException {

        Gson gson = new Gson();
        Socket socket = new Socket(ip, port);
        frmManteniment vista = new frmManteniment();
        
        /**
         * Generem objecte Json amb l'objecte modificar i les propietats que hi volem
         * afegir (accio, token i classe). 
         */
        
        JsonObject obtManteniment = new JsonObject();
        obtManteniment.add("mantenimiento", gson.toJsonTree(manteniment));
        obtManteniment.addProperty("accio", MODIFICAR);
        obtManteniment.addProperty("token", token);
        obtManteniment.addProperty("clase", "Mantenimiento.class");
        
         /**
         * Rebem un booleà que ens indica si s'ha fet correctament. 
         */

        com.enviaDades(obtManteniment, socket);
        Boolean resposta = com.repDades3(socket);
        System.out.println("La resposta es " + resposta);
        return resposta;

    }
    
      /**
     * Mètode que s'encarrega d'eliminar un manteniment. 
     * @param manteniment el que volem eliminar
     * @param token per poder parlar amb el server
     * @return
     * @throws IOException 
     */

    public boolean eliminarManteniment(Mantenimiento manteniment, String token) throws IOException {

        
        
        Gson gson = new Gson();
        Socket socket = new Socket(ip, port);
        
        /**
         * Generem objecte Json amb l'objecte empleat i les propietats que hi volem
         * afegir (accio, token i classe). 
         */

        JsonObject obtManteniment = new JsonObject();
        obtManteniment.add("mantenimiento", gson.toJsonTree(manteniment));
        obtManteniment.addProperty("accio", ELIMINAR);
        obtManteniment.addProperty("token", token);
        obtManteniment.addProperty("clase", "Mantenimiento.class");
        
        /**
         * Rebem un booleà que ens indica si s'ha fet correctament. 
         */

        com.enviaDades(obtManteniment, socket);
        Boolean resposta = com.repDades3(socket);

        return resposta;
    }

}
