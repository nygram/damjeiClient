
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
import static model.consultasManteniment.ELIMINAR;
import static model.consultasManteniment.INSERTAR;
import static model.consultasManteniment.LISTARID;
import static model.consultasManteniment.LLISTAR;
import static model.consultasManteniment.MODIFICAR;
import vista.frmCombustible;
import vista.frmManteniment;

/**
 *
 * @author JavierFernándezDíaz
 */
public class consultesCombustible {
    
    private frmCombustible vista;
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

    public consultesCombustible() {
    }
    
    public void carregaTaula(frmCombustible vista, String token) throws IOException {

        this.vista = vista;
        this.token = token;

        DefaultTableModel modeloTabla = new DefaultTableModel() {
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return false;
            }
        };
        vista.taulaCombustible.setModel(modeloTabla);
        vista.taulaCombustible.setRowSorter(new TableRowSorter<DefaultTableModel>(modeloTabla));
        vista.taulaCombustible.setAutoCreateRowSorter(true);
        vista.taulaCombustible.setBackground(Color.WHITE);
        vista.taulaCombustible.setSelectionBackground(new Color(250, 201, 104));
        vista.taulaCombustible.setOpaque(true);

        vista.btnModificar.setVisible(false);
        vista.jTabbedPane1.setSelectedIndex(0);
        
        /**
         * Inicialitzazem Socket i comDades, encarregats de la cominiació
         * amb el servidor
         */

        Socket socket = new Socket(ip, port);
        comDades com = new comDades();
        System.out.println("token " + token);
        Combustible co = new Combustible();

        Gson gson = new Gson();
        
         /**
         * Generem objecte Json amb l'objecte empleat i les propietats que hi volem
         * afegir (accio, token i classe).
         */

        JsonObject obtCombustible = new JsonObject();
        obtCombustible.add("combustible", gson.toJsonTree(co));
        obtCombustible.addProperty("accio", LLISTAR);
        obtCombustible.addProperty("token", token);
        obtCombustible.addProperty("clase", "Combustible.class");

        com.enviaDades(obtCombustible, socket);

        modeloTabla.addColumn("Id");
        modeloTabla.addColumn("Nombre");
        modeloTabla.addColumn("Precio");
        
        /**
         * Rebem les dades com a array de Objects. Fent recorrgut per l'array, 
         * recollim les dades i les afegim a les files de la taula
         */

        JsonArray combustible = com.repDades4(socket);
        for (JsonElement combustibles : combustible) {

            JsonObject combus = combustibles.getAsJsonObject();
            int id = combus.get("idcombustible").getAsInt();
            String nombre = combus.get("nombre").getAsString();
            Float precio = combus.get("precio").getAsFloat();

            Object[] fila = {id, nombre, precio};
            modeloTabla.addRow(fila);
        }

    }
      /**
     * Mètode que s'encarrega de rebre les dades d'un combustible concret per poder
     * emplenar els camps de la pestanya de detalls de combustible
     * @param codigo es la id que identifica a el manteniment
     * @param vista frmManteniments
     * @throws IOException 
     */

    public void carregaCombustible(int codigo, frmCombustible vista) throws IOException {

        this.vista = vista;
        this.token = token;
        
        /**
         * Inicialitzazem Socket i comDades, encarregats de la comuniació
         * amb el servidor
         */

        Socket socket = new Socket(ip, port);
        comDades com = new comDades();
        Combustible co = new Combustible();
        co.setIdcombustible(codigo);

        Gson gson = new Gson();
        
        /**
         * Generem objecte Json amb l'objecte combustible i les propietats que hi volem
         * afegir (accio, token i classe). 
         */

        JsonObject obtCombustible = new JsonObject();
        obtCombustible.add("combustible", gson.toJsonTree(co));
        obtCombustible.addProperty("accio", LISTARID);
        obtCombustible.addProperty("token", token);
        obtCombustible.addProperty("clase", "Combustible.class");

        com.enviaDades(obtCombustible, socket);
        
        /**
         * Rebem les dades com a JsonArray. Fent recorregut per l'array, 
         * recollim les dades i les afegim als textBox de la vista
         */

        JsonArray combustible = com.repDades4(socket);
        for (JsonElement combustibles : combustible) {

            JsonObject combus = combustibles.getAsJsonObject();
            int id = combus.get("idcombustible").getAsInt();
            String nombre = combus.get("nombre").getAsString();
            Float precio = combus.get("precio").getAsFloat();

            Combustible combus2 = new Combustible();
            combus2.setIdcombustible(id);
            combus2.setNombre(nombre);
            combus2.setPrecio(precio);

            vista.txtNom.setText(nombre);
            vista.txtPrecio.setText(precio.toString());

        }

    }
    
    public boolean insertarCombustible(Combustible combustible, String token) throws IOException {

        Gson gson = new Gson();
        Socket socket = new Socket(ip, port);
        frmCombustible vista = new frmCombustible();
        
        /**
         * Generem objecte Json amb l'objecte empleat i les propietats que hi volem
         * afegir (accio, token i classe). 
         */
        
        JsonObject obtCombustible = new JsonObject();
        obtCombustible.add("combustible", gson.toJsonTree(combustible));
        obtCombustible.addProperty("accio", INSERTAR);
        obtCombustible.addProperty("token", token);
        obtCombustible.addProperty("clase", "Combustible.class");
        
        /**
         * Rebem un booleà que ens indica si s'ha fet correctament. 
         */
        
        com.enviaDades(obtCombustible, socket);
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
    
    public boolean modificarCombustible(Combustible combustible, String token) throws IOException {

        Gson gson = new Gson();
        Socket socket = new Socket(ip, port);
        frmCombustible vista = new frmCombustible();
        
        /**
         * Generem objecte Json amb l'objecte modificar i les propietats que hi volem
         * afegir (accio, token i classe). 
         */
        
        JsonObject obtCombustible = new JsonObject();
        obtCombustible.add("combustible", gson.toJsonTree(combustible));
        obtCombustible.addProperty("accio", MODIFICAR);
        obtCombustible.addProperty("token", token);
        obtCombustible.addProperty("clase", "Combustible.class");
        
         /**
         * Rebem un booleà que ens indica si s'ha fet correctament. 
         */

        com.enviaDades(obtCombustible, socket);
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

    public boolean eliminarCombustible(Combustible combustible, String token) throws IOException {

        
        
        Gson gson = new Gson();
        Socket socket = new Socket(ip, port);
        
        /**
         * Generem objecte Json amb l'objecte empleat i les propietats que hi volem
         * afegir (accio, token i classe). 
         */

        JsonObject obtCombustible = new JsonObject();
        obtCombustible.add("combustible", gson.toJsonTree(combustible));
        obtCombustible.addProperty("accio", ELIMINAR);
        obtCombustible.addProperty("token", token);
        obtCombustible.addProperty("clase", "Combustible.class");
        
        /**
         * Rebem un booleà que ens indica si s'ha fet correctament. 
         */

        com.enviaDades(obtCombustible, socket);
        Boolean resposta = com.repDades3(socket);

        return resposta;
    }

    
}
