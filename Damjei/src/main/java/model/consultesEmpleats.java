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
 *
 * @author Javi
 */
public class consultesEmpleats extends conexion {

    private frmEmpleats vista;
    comDades com = new comDades();
    private Socket socket;
    private String token;
    public static final int LLISTAR = 5;
    public static final int INSERTAR = 2;
    public static final int ELIMINAR = 4;
    int port = 8180;
    String ip = "127.0.0.1";

    public consultesEmpleats() {
    }

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

        Socket socket = new Socket(ip, port);
        comDades com = new comDades();
        System.out.println("token " + token);
        Empleats emp = new Empleats();

        Gson gson = new Gson();

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

    public void carregaEmpleat(int codigo, frmEmpleats vista) throws IOException {

        this.vista = vista;
        this.token = token;
        this.vista = vista;
        
        
        Socket socket = new Socket(ip, port);
        comDades com = new comDades();
        Empleats emp = new Empleats();

        Gson gson = new Gson();
        
        JsonObject obtEmpleat = new JsonObject();
        obtEmpleat.add("empleat", gson.toJsonTree(emp));
        obtEmpleat.addProperty("accio", LLISTAR);
        obtEmpleat.addProperty("token", token);
        obtEmpleat.addProperty("clase", "Empleats.class");
        
        com.enviaDades(obtEmpleat, socket);
        
        Object[] empleat = com.repDades2(socket);
        for (Object empleats : empleat) {
            Empleats emp2 = gson.fromJson(empleats.toString(), Empleats.class);
            String dni = emp2.getDni();
            String nombre = emp2.getNom();
            String apellido = emp2.getApellidos();
            String contraseña = emp2.getContrasenya();
            Boolean administrador = emp2.getAdministrador();
            
            vista.txtNom.setText(nombre);
            vista.txtCognoms.setText(apellido);
            vista.txtNif.setText(dni);
            vista.txtContraseña.setText(contraseña);
            vista.rbtnAdministrador.setSelected(administrador);
            
            
            
        }

        /*
        try {

            Conexion con = new Conexion();

            Connection conexion = con.getConnection();

            ps = conexion.prepareStatement("Select * from tecnics where Id = ?");

            ps.setInt(1, ((codigo)));
            rs = ps.executeQuery();

            while (rs.next()) {
                entrad.txtNif.setText(rs.getString("Nif"));
                entrad.txtAdreca.setText(rs.getString("Adreça"));
                entrad.txtNom.setText(rs.getString("Nom"));
                entrad.txtCodipostal.setText(rs.getString("Codi_Postal"));
                entrad.txtCognoms.setText(rs.getString("Cognoms"));
                entrad.txtPoblacio.setText(rs.getString("poblacio"));
                entrad.txtCodi.setText(rs.getString("codi_tecnic"));
                entrad.txtTelefonParticular.setText(rs.getString("tel_particular"));
                entrad.txtTelefonEmpresa.setText(rs.getString("tel_empresa"));
                entrad.txtExtensio.setText(rs.getString("extensio"));
                entrad.txtId.setText(rs.getString("Id"));

            }

            rs.close();

        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("Error " + ex);
        }
         */
    }
     public boolean insertarEmpleat(Empleats empleat, String token) throws IOException{
         
        Gson gson = new Gson();
        Socket socket = new Socket(ip, port);
        frmEmpleats vista = new frmEmpleats();
        Object prova = empleat.getClass();
         System.out.println("prova "+prova);
         
        JsonObject obtEmpleat = new JsonObject();
        obtEmpleat.add("empleat", gson.toJsonTree(empleat));
        obtEmpleat.addProperty("accio", INSERTAR);
        obtEmpleat.addProperty("token", token);
        obtEmpleat.addProperty("clase", "Empleats.class");
        
        
         com.enviaDades(obtEmpleat, socket);
         Boolean resposta = Boolean.parseBoolean(com.repDades3(socket));
         return resposta;
         
         
         
         
         
     }
     
     public boolean eliminarEmpleat(Empleats empleat, String token) throws IOException{
         
        Gson gson = new Gson();
        Socket socket = new Socket(ip, port);
        frmEmpleats vista = new frmEmpleats();
         
        JsonObject obtEmpleat = new JsonObject();
        obtEmpleat.add("empleat", gson.toJsonTree(empleat));
        obtEmpleat.addProperty("accio", ELIMINAR);
        obtEmpleat.addProperty("token", token);
        obtEmpleat.addProperty("clase", "Empleats.class");
        
        
         com.enviaDades(obtEmpleat, socket);
         Boolean resposta = Boolean.parseBoolean(com.repDades3(socket));
         
          return resposta;
     }
    

}
