/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import Utils.Camps;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import model.Empleats;
import model.Mantenimiento;
import model.consultasManteniment;
import model.consultesEmpleats;
import vista.frmEmpleats;
import vista.frmManteniment;

/**
 * @author JavierFernándezDíaz Controlador de la part de Manteniment. S'encarregar de
 * comunicar la vista amb el model i de la part lògica. Implmenta la interface
 * ActionListener i MouseListener
 *
 */
public class ctrlManteniment implements ActionListener, MouseListener {

    private frmManteniment vistaManteniment;
    private consultasManteniment consulta;
    private Mantenimiento mantenimiento;
    private String token;
    
      /**
     * Constructor del controlador que s'inicialitza amb la vista (frmManteniment), 
     * consulta (consultasManteniment), mantenimiento (Mantenimiento) i el token. Afegeix Listeners
     * als botons i la taula.
     * 
     * @param vista. Objecte de frmManteniment
     * @param consulta. Objecte de consultasManteniment
     * @param mantenimiento. Objecte mantenimiento
     * @param token. Token rebut del servidor
     * @throws IOException 
     */

    public ctrlManteniment(frmManteniment vista, consultasManteniment consulta, Mantenimiento mantenimiento, String token) throws IOException {
        this.vistaManteniment = vista;
        this.consulta = consulta;
        this.token = token;
        this.mantenimiento = mantenimiento;
        System.out.println(mantenimiento);
        consulta.carregaTaula(vista, token);
        vista.taulaMantenimiento.addMouseListener(this);
        vista.btnInsertar.addMouseListener(this);
        vista.btnBorrar.addMouseListener(this);
        vista.btnNuevo.addMouseListener(this);
        vista.btnSalir.addMouseListener(this);
        vista.btnModificar.addMouseListener(this);

    }
    
    /**
     * Implementació métodes de la interficie
     * @param e 
     */

    @Override
    public void actionPerformed(ActionEvent e) {

    }
    
    /**
     * Si l'origen de l'esdeveniment és la taula mateniment, recollim les dades
     * de la fila on s'ha produït i les guardem. Si fa 2 clicks carreguem la segona pestanya 
     * amb el detall de l'empleat
     * @param me 
     */

    @Override
    public void mouseClicked(MouseEvent me) {
        if (me.getSource() == vistaManteniment.taulaMantenimiento) {
            int fila = vistaManteniment.taulaMantenimiento.getSelectedRow();
            String codigo = vistaManteniment.taulaMantenimiento.getValueAt(fila, 0).toString();
            vistaManteniment.txtId.setText(codigo);
            if (me.getClickCount() == 2) {
                vistaManteniment.jTabbedPane1.setSelectedIndex(1);
                vistaManteniment.btnModificar.setVisible(true);
                vistaManteniment.btnInsertar.setVisible(false);
                try {
                    consulta.carregaManteniment(Integer.parseInt(codigo), vistaManteniment);
                } catch (IOException ex) {
                    Logger.getLogger(ctrlEmpleats.class.getName()).log(Level.SEVERE, null, ex);
                }

            }

        }
        
        /**
         * Si l'origen de l'esdeveniment és el botó insertar
         * recollim les dades dels textBox i radiobutton, les assignem al
         * mateniment i les enviem al mètode insertarManteniment de la classe 
         * consultasManteniment per comunicar amb el servidor.
         * Si es correcte o no, apareix un JOptionPane.showMessageDialog que ens informa
         */
        
        if (me.getSource() == vistaManteniment.btnInsertar) {
            System.out.println("Insertar");
            String nombre = vistaManteniment.txtNom.getText();
            Float km = Float.valueOf(vistaManteniment.txtKm.getText());
            /*
            if (km == null){
                km = 0f;
            }*/
            mantenimiento.setNombre(nombre);
            mantenimiento.setKilometros_mantenimiento(km);
            
            
           

            try {
                Boolean resposta = consulta.insertarManteniment(mantenimiento, token);
                System.out.println("");
                if (resposta) {
                    JOptionPane.showMessageDialog(null, "Afegit correctament");
                    consulta.carregaTaula(vistaManteniment, token);

                } else {
                    JOptionPane.showMessageDialog(null, "No afegit correctament");

                }
            } catch (IOException ex) {
                Logger.getLogger(ctrlEmpleats.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
         /**
         * Si l'origen de l'esdeveniment és el botó Borrar, recollim el valor de 
         * la columna 1 de la fila escollida (nom), l'assignem al vehicle i 
         * l'enviem al mètode eliminarVehicle de consultesVehicle
         * Si s'inserta correctament o no ens informa
         */

        if (me.getSource() == vistaManteniment.btnBorrar) {
            

            int fila = vistaManteniment.taulaMantenimiento.getSelectedRow();
            String nombre = (String)vistaManteniment.taulaMantenimiento.getValueAt(fila, 1);

            mantenimiento.setNombre(nombre);

            try {
                if (consulta.eliminarManteniment(mantenimiento, token)) {
                    vistaManteniment.jTabbedPane1.setSelectedIndex(0);
                    JOptionPane.showMessageDialog(null, "Borrado correctamente");
                    consulta.carregaTaula(vistaManteniment, token);

                } else {
                    JOptionPane.showMessageDialog(null, "No borrado");
                }
            } catch (IOException ex) {
                Logger.getLogger(ctrlEmpleats.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        
        /**
         * Si l'origen de l'esdeveniment és el botó Nuevo, obrim la segona
         * pestanya buidant tots els camps amb el mètode limpiarCampos de la
         * classe Campos del package Utils
        */
        
        if (me.getSource() == vistaManteniment.btnNuevo) {
            vistaManteniment.jTabbedPane1.setSelectedIndex(1);
            Camps.netejaCamps(vistaManteniment.jPanel2);
            vistaManteniment.btnModificar.setVisible(false);
            vistaManteniment.btnInsertar.setVisible(true);

        }
        
        /**
         * Si l'origen de l'esdeveniment és el botó Modificar, recollim el valor dels 
         * textBox, els assignem al manteniment i 
         * l'enviem al mètode modificarManteniment de consultasManteniment
         * Si modifica correctament o no ens informa
         */
        
        if (me.getSource() == vistaManteniment.btnModificar) {
            System.out.println("modifica");
            String nombre = vistaManteniment.txtNom.getText();
            Float km = 0.0f;
            if (Float.valueOf(vistaManteniment.txtKm.getText()) != null){
                km = Float.valueOf(vistaManteniment.txtKm.getText());
            }
            String fecha = vistaManteniment.txtFecha.getText();
           
            
            mantenimiento.setNombre(nombre);
            mantenimiento.setKilometros_mantenimiento(km);
            mantenimiento.setFecha_mantenimiento(fecha);
           

            try {
                Boolean resposta = consulta.modificarManteniment(mantenimiento, token);
                if (resposta) {
                    JOptionPane.showMessageDialog(null, "Modificat correctament");
                    consulta.carregaTaula(vistaManteniment, token);

                } else {
                    JOptionPane.showMessageDialog(null, "No modificat");

                }
            } catch (IOException ex) {
                Logger.getLogger(ctrlEmpleats.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        /**
         * Si l'origen de l'esdeveniment és el botó Salir, 
         * sortim de la pantalla de Manteniments i tornem a la
         * d'Opcions
         */

        if (me.getSource() == vistaManteniment.btnSalir) {
            vistaManteniment.dispose();

        }

    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

}

