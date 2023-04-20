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
import static model.consultesEmpleats.LLISTAR;
import vista.frmEmpleats;
import vista.frmVehiculos;

/**
 *
 * @author JavierFernándezDíaz
 */
public class consultasVehiculo {
    
    private frmVehiculos vista;
    comDades com = new comDades();
    private Socket socket;
    private String token;
    public static final int LLISTAR = 5;
    public static final int INSERTAR = 2;
    public static final int ELIMINAR = 4;
    public static final int LISTARID = 7;
    int port = 8180;
    String ip = "127.0.0.1";
    
    public consultasVehiculo() {
    }

    public void carregaTaula(frmVehiculos vista, String token) throws IOException {

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
        Vehiculos veh = new Vehiculos();

        Gson gson = new Gson();

        JsonObject obtEmpleat = new JsonObject();
        obtEmpleat.add("vehiculo", gson.toJsonTree(veh));
        obtEmpleat.addProperty("accio", LLISTAR);
        obtEmpleat.addProperty("token", token);
        obtEmpleat.addProperty("clase", "Empleats.class");

        com.enviaDades(obtEmpleat, socket);

        modeloTabla.addColumn("Id");
        modeloTabla.addColumn("Marca");
        modeloTabla.addColumn("Modelo");
        modeloTabla.addColumn("Matricula");

        Object[] vehiculo = com.repDades2(socket);
        for (Object vehiculos : vehiculo) {
            Vehiculos veh2 = gson.fromJson(vehiculos.toString(), Vehiculos.class);
            int id = veh2.getIdvehiculos();
            String marca = veh2.getMarca();
            String modelo = veh2.getModelo();
            String matricula = veh2.getMatricula();
            Object[] fila = {id, marca, modelo, matricula};
            modeloTabla.addRow(fila);
        }
     

    }
    
    
    
    
    
}
