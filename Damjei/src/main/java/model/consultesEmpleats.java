/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import com.comDades;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.awt.Color;
import java.io.IOException;
import java.net.Socket;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import vista.frmEmpleats;

/**
 * classe que s'encarrega de recollir les dades enviades per el controlador
 * i, un cop formatades, enviarles al servidor amb l'acció que volem fer 
 * amb aquestes dades: llistar, insertar, eliminar, modificar, llistar un únic
 * empleat
 * Seguidament recull la resposta i la tracta segons convingui
 * @author JavierFernándezDíaz
 */
public class consultesEmpleats {
    
    
    private frmEmpleats vista;
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
    

    public consultesEmpleats() {
    }
    
    /**
     * Mètode que s'encarrega d'emplenar la taula. Rep la vista i el token.
     * Crea el model de la taula. Crida al servidor per obtenir les dades per
     * emplenar la taula i emplena la taula amb aquestes dades.
     * @param vista frmEmpleats
     * @param token 
     * @throws IOException 
     */

    public void carregaTaula(frmEmpleats vista, String token) throws IOException {

        this.vista = vista;
        this.token = token;

        DefaultTableModel modeloTabla = new DefaultTableModel() {
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return false;
            }
        };
        vista.taulaEmpleats.setModel(modeloTabla);
        vista.taulaEmpleats.setRowSorter(new TableRowSorter<DefaultTableModel>(modeloTabla));
        vista.taulaEmpleats.setAutoCreateRowSorter(true);
        vista.taulaEmpleats.setBackground(Color.WHITE);
        vista.taulaEmpleats.setSelectionBackground(new Color(250, 201, 104));
        vista.taulaEmpleats.setOpaque(true);

        vista.btnModificar.setVisible(false);
        vista.jTabbedPane1.setSelectedIndex(0);
        
        /**
         * Inicialitzazem Socket i comDades, encarregats de la cominiació
         * amb el servidor
         */
        Socket socket = new Socket(ip, port);
        comDades com = new comDades();
        Empleats emp = new Empleats();

        Gson gson = new Gson();
        
        /**
         * Generem objecte Json amb l'objecte empleat i les propietats que hi volem
         * afegir (accio, token i classe).
         */

        JsonObject obtEmpleat = new JsonObject();
        obtEmpleat.add("empleat", gson.toJsonTree(emp));
        obtEmpleat.addProperty("accio", LLISTAR);
        obtEmpleat.addProperty("token", token);
        obtEmpleat.addProperty("clase", "Empleats.class");

        com.enviaDades(obtEmpleat, socket);

        modeloTabla.addColumn("Id");
        modeloTabla.addColumn("Dni");
        modeloTabla.addColumn("Nombre");
        modeloTabla.addColumn("Apellidos");
        modeloTabla.addColumn("Administrador");
        
        /**
         * Rebem les dades com a array de Objects. Fent recorrgut per l'array, 
         * recollim les dades i les afegim a les files de la taula
         */

        Object[] empleat = com.repDades2(socket);
        for (Object empleats : empleat) {
            Empleats emp2 = gson.fromJson(empleats.toString(), Empleats.class);
            int id = emp2.getIdempleado();
            String dni = emp2.getDni();
            String nombre = emp2.getNom();
            String apellido = emp2.getApellidos();
            Boolean administrador = emp2.getAdministrador();
            
            Object[] fila = {id, dni, nombre, apellido, administrador};
            modeloTabla.addRow(fila);
        }

    }
    
    /**
     * Mètode que s'encarrega de rebre les dades d'un empleat concret per poder
     * emplenar els camps de la pestanya de detalls de empleat
     * @param codigo es la id que identifica a l'empleat
     * @param vista frmEmplats
     * @throws IOException 
     */

    public void carregaEmpleat(int codigo, frmEmpleats vista) throws IOException {

        this.vista = vista;
        this.token = token;
        this.vista = vista;
        
        /**
         * Inicialitzazem Socket i comDades, encarregats de la cominiació
         * amb el servidor
         */
        
        Socket socket = new Socket(ip, port);
        comDades com = new comDades();
        Empleats emp = new Empleats();
        emp.setIdempleado(codigo);

        Gson gson = new Gson();
        
        /**
         * Generem objecte Json amb l'objecte empleat i les propietats que hi volem
         * afegir (accio, token i classe). 
         */
        
        JsonObject obtEmpleat = new JsonObject();
        obtEmpleat.add("empleat", gson.toJsonTree(emp));
        obtEmpleat.addProperty("accio", LISTARID);
        obtEmpleat.addProperty("token", token);
        obtEmpleat.addProperty("clase", "Empleats.class");
        
        com.enviaDades(obtEmpleat, socket);
        
        /**
         * Rebem les dades com a array de Objects. Fent recorrgut per l'array, 
         * recollim les dades i les afegim als textBox de la vista
         */
        
        Object[] empleat = com.repDades2(socket);
        for (Object empleats : empleat) {
            Empleats emp2 = gson.fromJson(empleats.toString(), Empleats.class);
            int idempleado = emp2.getIdempleado();
            String dni = emp2.getDni();
            String nombre = emp2.getNom();
            String apellido = emp2.getApellidos();
            String contraseña = emp2.getContrasenya();
            boolean administrador = emp2.getAdministrador();
            String categoria = emp2.getCategoria();
            System.out.println("Conductor es :"+categoria);
            
            
            vista.txtNom.setText(nombre);
            vista.txtCognoms.setText(apellido);
            vista.txtNif.setText(dni);
            vista.txtContraseña.setText(contraseña);
            if (administrador){
                vista.rbtnAdministrador.setSelected(true);
            }else{
                vista.rbtnAdministrador.setSelected(false);
            }
            vista.cmbCategoria.setSelectedItem(categoria);
                
        }
            
    }
    
    /**
     * Mètode que s'encarrega d'afegir un nou empleat. 
     * @param empleat el que volem afegir
     * @param token per poder parlar amb el server
     * @return
     * @throws IOException 
     */
           
    
     public boolean insertarEmpleat(Empleats empleat, String token) throws IOException{
         
        Gson gson = new Gson();
                
        Socket socket = new Socket(ip, port);
        frmEmpleats vista = new frmEmpleats();
        
        /**
         * Generem objecte Json amb l'objecte empleat i les propietats que hi volem
         * afegir (accio, token i classe). 
         */
         
        JsonObject obtEmpleat = new JsonObject();
        obtEmpleat.add("empleat", gson.toJsonTree(empleat));
        obtEmpleat.addProperty("accio", INSERTAR);
        obtEmpleat.addProperty("token", token);
        obtEmpleat.addProperty("clase", "Empleats.class");
        
        
        /**
         * Rebem un booleà que ens indica si s'ha fet correctament. 
         */
        
         com.enviaDades(obtEmpleat, socket);
         Boolean resposta = com.repDades3(socket);
         return resposta;
         
         
         
         
         
     }
     /**
     * Mètode que s'encarrega de modificar un empleat existent
     * @param empleat el que volem modificar
     * @param token per poder parlar amb el server
     * @return
     * @throws IOException 
     */
     
     public boolean modificarEmpleat(Empleats empleat, String token) throws IOException{
         
        Gson gson = new Gson();
        Socket socket = new Socket(ip, port);
        frmEmpleats vista = new frmEmpleats();
        
        /**
         * Generem objecte Json amb l'objecte empleat i les propietats que hi volem
         * afegir (accio, token i classe). 
         */
         
        JsonObject obtEmpleat = new JsonObject();
        obtEmpleat.add("empleat", gson.toJsonTree(empleat));
        obtEmpleat.addProperty("accio", MODIFICAR);
        obtEmpleat.addProperty("token", token);
        obtEmpleat.addProperty("clase", "Empleats.class");
        
        /**
         * Rebem un booleà que ens indica si s'ha fet correctament. 
         */
        
         com.enviaDades(obtEmpleat, socket);
         Boolean resposta = com.repDades3(socket);
         return resposta;
         
         
         
         
         
     }
     
     /**
     * Mètode que s'encarrega d'eliminar unempleat. 
     * @param empleat el que volem eliminar
     * @param token per poder parlar amb el server
     * @return
     * @throws IOException 
     */
     
     
     public boolean eliminarEmpleat(Empleats empleat, String token) throws IOException{
         
        Gson gson = new Gson();
        Socket socket = new Socket(ip, port);
        
        /**
         * Generem objecte Json amb l'objecte empleat i les propietats que hi volem
         * afegir (accio, token i classe). 
         */
        
        JsonObject obtEmpleat = new JsonObject();
        obtEmpleat.add("empleat", gson.toJsonTree(empleat));
        obtEmpleat.addProperty("accio", ELIMINAR);
        obtEmpleat.addProperty("token", token);
        obtEmpleat.addProperty("clase", "Empleats.class");
        
        /**
         * Rebem un booleà que ens indica si s'ha fet correctament. 
         */
        
         com.enviaDades(obtEmpleat, socket);
         Boolean resposta = com.repDades3(socket);
         
          return resposta;
     }
    

}
