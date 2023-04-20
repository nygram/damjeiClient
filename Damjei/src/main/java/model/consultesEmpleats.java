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
public class consultesEmpleats {

    private frmEmpleats vista;
    comDades com = new comDades();
    private Socket socket;
    private String token;
    public static final int LLISTAR = 5;
    public static final int INSERTAR = 2;
    public static final int ELIMINAR = 4;
    public static final int LISTARID = 7;
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
        emp.setIdempleado(codigo);

        Gson gson = new Gson();
        
        JsonObject obtEmpleat = new JsonObject();
        obtEmpleat.add("empleat", gson.toJsonTree(emp));
        obtEmpleat.addProperty("accio", LISTARID);
        obtEmpleat.addProperty("token", token);
        obtEmpleat.addProperty("clase", "Empleats.class");
        
        com.enviaDades(obtEmpleat, socket);
        
        Object[] empleat = com.repDades2(socket);
        for (Object empleats : empleat) {
            Empleats emp2 = gson.fromJson(empleats.toString(), Empleats.class);
            System.out.println("Empleat "+emp2);
            int idempleado = emp2.getIdempleado();
            String dni = emp2.getDni();
            System.out.println("dni "+dni);
            String nombre = emp2.getNom();
            String apellido = emp2.getApellidos();
            String contraseña = emp2.getContrasenya();
            boolean administrador = emp2.getAdministrador();
            System.out.println("Bool `+"+administrador);
            
            vista.txtNom.setText(nombre);
            vista.txtCognoms.setText(apellido);
            vista.txtNif.setText(dni);
            vista.txtContraseña.setText(contraseña);
            if (administrador){
                vista.rbtnAdministrador.setSelected(true);
            }else{
                vista.rbtnAdministrador.setSelected(false);
            }
                
        }
            
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
         Boolean resposta = com.repDades3(socket);
         System.out.println("La resposta es "+resposta);
         return resposta;
         
         
         
         
         
     }
     
     public boolean eliminarEmpleat(Empleats empleat, String token) throws IOException{
         
        Gson gson = new Gson();
        Socket socket = new Socket(ip, port);
        
        JsonObject obtEmpleat = new JsonObject();
        obtEmpleat.add("empleat", gson.toJsonTree(empleat));
        obtEmpleat.addProperty("accio", ELIMINAR);
        obtEmpleat.addProperty("token", token);
        obtEmpleat.addProperty("clase", "Empleats.class");
        
        
         com.enviaDades(obtEmpleat, socket);
         Boolean resposta = com.repDades3(socket);
         
          return resposta;
     }
    

}
