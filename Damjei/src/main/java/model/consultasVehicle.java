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
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import static model.consultesEmpleats.ELIMINAR;
import static model.consultesEmpleats.INSERTAR;
import static model.consultesEmpleats.LISTARID;
import static model.consultesEmpleats.LLISTAR;
import vista.frmEmpleats;
import vista.frmVehicle;

/**
 *
 * @author JavierFernándezDíaz
 */
public class consultasVehicle {
    
    private frmVehicle vista;
    comDades com = new comDades();
    private Socket socket;
    private String token;
    public static final int LLISTAR = 5;
    public static final int INSERTAR = 2;
    public static final int ELIMINAR = 4;
    public static final int LISTARID = 7;
    int port = 8180;
    String ip = "127.0.0.1";
    
    public consultasVehicle() {
    }

    public void carregaTaula(frmVehicle vista, String token) throws IOException {

        this.vista = vista;
        this.token = token;

        DefaultTableModel modeloTabla = new DefaultTableModel() {
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return false;
            }
        };
        vista.taulaVehicles.setModel(modeloTabla);
        vista.taulaVehicles.setRowSorter(new TableRowSorter<DefaultTableModel>(modeloTabla));
        vista.taulaVehicles.setAutoCreateRowSorter(true);
        vista.taulaVehicles.setBackground(Color.WHITE);
        vista.taulaVehicles.setSelectionBackground(new Color(250, 201, 104));
        vista.taulaVehicles.setOpaque(true);

        vista.btnModificar.setVisible(false);
        vista.jTabbedPane1.setSelectedIndex(0);

        Socket socket = new Socket(ip, port);
        comDades com = new comDades();
        System.out.println("token " + token);
        Vehicle veh = new Vehicle();

        Gson gson = new Gson();

        JsonObject obtVehiculo = new JsonObject();
        obtVehiculo.add("vehicle", gson.toJsonTree(veh));
        obtVehiculo.addProperty("accio", LLISTAR);
        obtVehiculo.addProperty("token", token);
        obtVehiculo.addProperty("clase", "Vehicle.class");

        com.enviaDades(obtVehiculo, socket);

        modeloTabla.addColumn("Id");
        modeloTabla.addColumn("Marca");
        modeloTabla.addColumn("Modelo");
        modeloTabla.addColumn("Matricula");

        Object[] vehiculo = com.repDades2(socket);
        System.out.println(vehiculo);
        for (Object vehiculos : vehiculo) {
            Vehicle veh2 = gson.fromJson(vehiculos.toString(), Vehicle.class);
            int id = veh2.getIdvehiculo();
            String marca = veh2.getMarca();
            String modelo = veh2.getModelo();
            String matricula = veh2.getMatricula();
            Object[] fila = {id, marca, modelo, matricula};
            modeloTabla.addRow(fila);
        }
     

    }
 public void carregaVehicle(int codigo, frmVehicle vista) throws IOException {

        this.vista = vista;
        this.token = token;
        this.vista = vista;
        
        
        Socket socket = new Socket(ip, port);
        comDades com = new comDades();
        Vehicle veh = new Vehicle();
        veh.setIdvehiculo(codigo);

        Gson gson = new Gson();
        
        JsonObject obtVehicle = new JsonObject();
        obtVehicle.add("vehicle", gson.toJsonTree(veh));
        obtVehicle.addProperty("accio", LISTARID);
        obtVehicle.addProperty("token", token);
        obtVehicle.addProperty("clase", "Vehicle.class");
        
        com.enviaDades(obtVehicle, socket);
        
        Object[] vehicle = com.repDades2(socket);
        for (Object vehicles : vehicle) {
            Vehicle veh2 = gson.fromJson(vehicles.toString(), Vehicle.class);
            System.out.println("Empleat "+veh2);
            int idvehiculo = veh2.getIdvehiculo();
            String marca = veh2.getMarca();
            String modelo = veh2.getModelo();
            String matricula = veh2.getMatricula();
            Float kilometros_alta = veh2.getKilometros_alta();
            Float kilometros_actu = veh2.getKilometros_actuales();
            String fecha_alta = veh2.getFecha_alta();
            String fecha_baja = veh2.getFecha_baja();
            
            vista.txtMatricula.setText(matricula);
            vista.txtMarca.setText(marca);
            vista.txtModel.setText(modelo);
            vista.txt_KmAlta.setText((kilometros_alta).toString());
            vista.txtKmActu.setText((kilometros_actu).toString());
            vista.txtDataAlta.setText(fecha_alta);
            vista.txtDataBaixa.setText(fecha_baja);
                
        }
            
    }
           
    
     public boolean insertarVehicle(Vehicle vehicle, String token) throws IOException{
         
        Gson gson = new Gson();
        Socket socket = new Socket(ip, port);
        frmVehicle vista = new frmVehicle();
        Object prova = vehicle.getClass();
        System.out.println("prova "+prova);
         
        JsonObject obtVehicle = new JsonObject();
        obtVehicle.add("vehicle", gson.toJsonTree(vehicle));
        obtVehicle.addProperty("accio", INSERTAR);
        obtVehicle.addProperty("token", token);
        obtVehicle.addProperty("clase", "Vehicle.class");
        
        
         com.enviaDades(obtVehicle, socket);
         Boolean resposta = com.repDades3(socket);
         System.out.println("La resposta es "+resposta);
         return resposta;
         
         
         
         
         
     }
     
     public boolean eliminarVehicle(Vehicle vehicle, String token) throws IOException{
         
        Gson gson = new Gson();
        Socket socket = new Socket(ip, port);
        
        JsonObject obtVehicle = new JsonObject();
        obtVehicle.add("vehicle", gson.toJsonTree(vehicle));
        obtVehicle.addProperty("accio", ELIMINAR);
        obtVehicle.addProperty("token", token);
        obtVehicle.addProperty("clase", "Vehicle.class");
        
        
         com.enviaDades(obtVehicle, socket);
         Boolean resposta = com.repDades3(socket);
         
          return resposta;
     }
    

}
