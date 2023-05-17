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
import model.Combustible;
import model.Mantenimiento;
import model.consultasManteniment;
import model.consultesCombustible;
import vista.frmCombustible;
import vista.frmManteniment;

/**
 *
 * @author JavierFernándezDíaz
 */
public class ctrlCombustible implements ActionListener, MouseListener{
    
    private frmCombustible vistaCombustible;
    private consultesCombustible consulta;
    private Combustible combustible;
    private String token;
    
      /**
     * Constructor del controlador que s'inicialitza amb la vista (frmCombustible), 
     * consulta (consultesCombustible), empleat (combustible) i el token. Afegeix Listeners
     * als botons i la taula.
     * 
     * @param vista. Objecte de frmCombustible
     * @param consulta. Objecte de consultesCombustible
     * @param combustible. Objecte combustible
     * @param token. Token rebut del servidor
     * @throws IOException 
     */

    public ctrlCombustible(frmCombustible vista, consultesCombustible consulta, Combustible combustible, String token) throws IOException {
        this.vistaCombustible = vista;
        this.consulta = consulta;
        this.token = token;
        this.combustible = combustible;
        consulta.carregaTaula(vista, token);
        vista.taulaCombustible.addMouseListener(this);
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
        if (me.getSource() == vistaCombustible.taulaCombustible) {
            int fila = vistaCombustible.taulaCombustible.getSelectedRow();
            String codigo = vistaCombustible.taulaCombustible.getValueAt(fila, 0).toString();
            vistaCombustible.txtId.setText(codigo);
            if (me.getClickCount() == 2) {
                vistaCombustible.jTabbedPane1.setSelectedIndex(1);
                vistaCombustible.btnModificar.setVisible(true);
                vistaCombustible.btnInsertar.setVisible(false);
                try {
                    consulta.carregaCombustible(Integer.parseInt(codigo), vistaCombustible);
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
        
        if (me.getSource() == vistaCombustible.btnInsertar) {
            System.out.println("Insertar");
            String nombre = vistaCombustible.txtNom.getText();
            Float precio = Float.valueOf(vistaCombustible.txtPrecio.getText());
            
            combustible.setNombre(nombre);
            combustible.setPrecio(precio);
           

            try {
                Boolean resposta = consulta.insertarCombustible(combustible, token);
                System.out.println("");
                if (resposta) {
                    JOptionPane.showMessageDialog(null, "Afegit correctament");
                    consulta.carregaTaula(vistaCombustible, token);

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

        if (me.getSource() == vistaCombustible.btnBorrar) {
            

            int fila = vistaCombustible.taulaCombustible.getSelectedRow();
            String nombre = vistaCombustible.taulaCombustible.getValueAt(fila, 1).toString();
            System.out.println("nombre es "+nombre);
            Float precio = Float.parseFloat(vistaCombustible.txtPrecio.getText());

            combustible.setNombre(nombre);
            combustible.setPrecio(precio);

            try {
                if (consulta.eliminarCombustible(combustible, token)) {
                    vistaCombustible.jTabbedPane1.setSelectedIndex(0);
                    JOptionPane.showMessageDialog(null, "Borrado correctamente");
                    consulta.carregaTaula(vistaCombustible, token);

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
        
        if (me.getSource() == vistaCombustible.btnNuevo) {
            vistaCombustible.jTabbedPane1.setSelectedIndex(1);
            Camps.netejaCamps(vistaCombustible.jPanel2);
            vistaCombustible.btnModificar.setVisible(false);
            vistaCombustible.btnInsertar.setVisible(true);

        }
        
        /**
         * Si l'origen de l'esdeveniment és el botó Modificar, recollim el valor dels 
         * textBox, els assignem al manteniment i 
         * l'enviem al mètode modificarManteniment de consultasManteniment
         * Si modifica correctament o no ens informa
         */
        
        if (me.getSource() == vistaCombustible.btnModificar) {
            System.out.println("modifica");
            String nombre = vistaCombustible.txtNom.getText();
            Float precio = Float.parseFloat(vistaCombustible.txtPrecio.getText());
                        
            combustible.setNombre(nombre);
            combustible.setPrecio(precio);
            
           

            try {
                Boolean resposta = consulta.modificarCombustible(combustible, token);
                if (resposta) {
                    JOptionPane.showMessageDialog(null, "Modificat correctament");
                    consulta.carregaTaula(vistaCombustible, token);

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

        if (me.getSource() == vistaCombustible.btnSalir) {
            vistaCombustible.dispose();

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

