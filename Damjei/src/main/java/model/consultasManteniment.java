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
 *
 * @author Javi
 */
public class consultasManteniment {

    private frmManteniment vista;
    comDades com = new comDades();
    private Socket socket;
    private String token;
    public static final int LLISTAR = 5;
    public static final int INSERTAR = 2;
    public static final int ELIMINAR = 4;
    public static final int LISTARID = 7;
    int port = 8180;
    String ip = "127.0.0.1";

    public consultasManteniment() {
    }

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

        Socket socket = new Socket(ip, port);
        comDades com = new comDades();
        System.out.println("token " + token);
        Mantenimiento man = new Mantenimiento();

        Gson gson = new Gson();

        JsonObject obtMantenimiento = new JsonObject();
        obtMantenimiento.add("mantenimiento", gson.toJsonTree(man));
        obtMantenimiento.addProperty("accio", LLISTAR);
        obtMantenimiento.addProperty("token", token);
        obtMantenimiento.addProperty("clase", "Mantenimiento.class");

        com.enviaDades(obtMantenimiento, socket);

        modeloTabla.addColumn("Id");
        modeloTabla.addColumn("Nombre");
        modeloTabla.addColumn("Km mantenimiento");

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

    public void carregaManteniment(int codigo, frmManteniment vista) throws IOException {

        this.vista = vista;
        this.token = token;

        Socket socket = new Socket(ip, port);
        comDades com = new comDades();
        Mantenimiento man = new Mantenimiento();
        man.setIdmantenimiento(codigo);

        Gson gson = new Gson();

        JsonObject obtManteniment = new JsonObject();
        obtManteniment.add("mantenimiento", gson.toJsonTree(man));
        obtManteniment.addProperty("accio", LISTARID);
        obtManteniment.addProperty("token", token);
        obtManteniment.addProperty("clase", "Mantenimiento.class");

        com.enviaDades(obtManteniment, socket);

        JsonArray mantenimiento = com.repDades4(socket);
        for (JsonElement mantenimientos : mantenimiento) {

            JsonObject manteniments = mantenimientos.getAsJsonObject();
            int id = manteniments.get("idmantenimiento").getAsInt();
            String nombre = manteniments.get("nombre").getAsString();
            Float km = null;
            if (manteniments.has("kilometros_mantenimiento")) {
                km = manteniments.get("kilometros_mantenimiento").getAsFloat();
            }

            Mantenimiento man2 = new Mantenimiento();
            man2.setIdmantenimiento(id);
            man2.setNombre(nombre);
            man2.setKilometros_mantenimiento(km);

            vista.txtNom.setText(nombre);
            vista.txtKm.setText(km.toString());

        }

    }

    public boolean insertarManteniment(Mantenimiento manteniment, String token) throws IOException {

        Gson gson = new Gson();
        Socket socket = new Socket(ip, port);
        frmManteniment vista = new frmManteniment();
        
        JsonObject obtManteniment = new JsonObject();
        obtManteniment.add("manteniment", gson.toJsonTree(manteniment));
        obtManteniment.addProperty("accio", INSERTAR);
        obtManteniment.addProperty("token", token);
        obtManteniment.addProperty("clase", "Mantenimiento.class");

        com.enviaDades(obtManteniment, socket);
        Boolean resposta = com.repDades3(socket);
        System.out.println("La resposta es " + resposta);
        return resposta;

    }

    public boolean eliminarManteniment(Mantenimiento manteniment, String token) throws IOException {

        
        
        Gson gson = new Gson();
        Socket socket = new Socket(ip, port);

        JsonObject obtManteniment = new JsonObject();
        obtManteniment.add("manteniment", gson.toJsonTree(manteniment));
        obtManteniment.addProperty("accio", ELIMINAR);
        obtManteniment.addProperty("token", token);
        obtManteniment.addProperty("clase", "Mantenimiento.class");

        com.enviaDades(obtManteniment, socket);
        Boolean resposta = com.repDades3(socket);

        return resposta;
    }

}
