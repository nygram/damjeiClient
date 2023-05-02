/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import Utils.Campos;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import model.Empleats;
import model.consultesEmpleats;
import vista.frmEmpleats;
import vista.frmLogin;
import vista.frmOpcions;


/**
 * @author JavierFernándezDíaz Controlador de la part de Empleat. S'encarregar de
 * comunicar la vista amb el model i de la part lògica. Implmenta la interface
 * ActionListener i MouseListener
 *
 */

public class ctrlEmpleats implements ActionListener, MouseListener {

    private frmEmpleats vistaEmpleats;
    private consultesEmpleats consulta;
    private Empleats empleat;
    private String token;
    
    /**
     * Constructor del controlador que s'inicialitza amb la vista (frmEmpleat), 
     * consulta (consultesEmpleats), empleat (Empleat) i el token. Afegeix Listeners
     * als botons i la taula.
     * 
     * @param vista. Objecte de frmEmpleats
     * @param consulta. Objecte de consulteEmpleats
     * @param empleat. Objecte empleat
     * @param token. Token rebut del servidor
     * @throws IOException 
     */

    public ctrlEmpleats(frmEmpleats vista, consultesEmpleats consulta, Empleats empleat, String token) throws IOException {
        this.vistaEmpleats = vista;
        this.consulta = consulta;
        this.token = token;
        this.empleat = empleat;
        consulta.carregaTaula(vista, token);
        vista.taulaEmpleats.addMouseListener(this);
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
     * Si l'origen de l'esdeveniment és la taula empleats, recollim les dades
     * de la fila on s'ha produït i les guardem. Si fa 2 clicks carreguem la segona pestanya 
     * amb el detall de l'empleat
     * @param me 
     */
    
    @Override
    public void mouseClicked(MouseEvent me) {
        if (me.getSource() == vistaEmpleats.taulaEmpleats) {
            int fila = vistaEmpleats.taulaEmpleats.getSelectedRow();
            String codigo = vistaEmpleats.taulaEmpleats.getValueAt(fila, 0).toString();
            vistaEmpleats.txtId.setText(codigo);
            if (me.getClickCount() == 2) {
                vistaEmpleats.jTabbedPane1.setSelectedIndex(1);
                vistaEmpleats.btnModificar.setVisible(true);
                vistaEmpleats.btnInsertar.setVisible(false);
                try {
                    consulta.carregaEmpleat(Integer.parseInt(codigo), vistaEmpleats);
                } catch (IOException ex) {
                    Logger.getLogger(ctrlEmpleats.class.getName()).log(Level.SEVERE, null, ex);
                }

            }

        }
        /**
         * Si l'origen de l'esdeveniment és el botó insertar
         * recollim les dades dels textBox i radiobutton, les assignem a
         * l'empleat i les enviem al mètode insertarEmpleat de la classe 
         * consultesEmpleats per comunicar amb el servidor.
         * Si es correcte o no, apareix un JOptionPane.showMessageDialog que ens informa
         */
        
        if (me.getSource() == vistaEmpleats.btnInsertar) {
            String nom = vistaEmpleats.txtNom.getText();
            String apellido = vistaEmpleats.txtCognoms.getText();
            String dni = vistaEmpleats.txtNif.getText();
            String contrasenya = vistaEmpleats.txtContraseña.getText();
            Boolean administrador = vistaEmpleats.rbtnAdministrador.isSelected();

            empleat.setNom(nom);
            empleat.setApellidos(apellido);
            empleat.setDni(dni);
            empleat.setCategoria("rrhh");
            empleat.setEmpresaid(1);
            empleat.setContrasenya(contrasenya);
            empleat.setAdministrador(administrador);

            try {
                Boolean resposta = consulta.insertarEmpleat(empleat, token);
                System.out.println("");
                if (resposta) {
                    JOptionPane.showMessageDialog(null, "Afegit correctament");
                    consulta.carregaTaula(vistaEmpleats, token);

                } else {
                    JOptionPane.showMessageDialog(null, "No afegit correctament");

                }
            } catch (IOException ex) {
                Logger.getLogger(ctrlEmpleats.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        /**
         * Si l'origen de l'esdeveniment és el botó Borrar, recollim el valor de 
         * la columna 2 de la fila escollida (DNI), l'assignem a l'empleat i 
         * l'enviem al mètode eliminarEmpleat de consultesEmpleats
         * Si s'inserta correctament o no ens informa
         */

        if (me.getSource() == vistaEmpleats.btnBorrar) {

            int fila = vistaEmpleats.taulaEmpleats.getSelectedRow();
            String dni = vistaEmpleats.taulaEmpleats.getValueAt(fila, 1).toString();

            empleat.setDni(dni);

            try {
                if (consulta.eliminarEmpleat(empleat, token)) {
                    vistaEmpleats.jTabbedPane1.setSelectedIndex(0);
                    JOptionPane.showMessageDialog(null, "Borrado correctamente");
                    consulta.carregaTaula(vistaEmpleats, token);

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
        
        if (me.getSource() == vistaEmpleats.btnNuevo) {
            vistaEmpleats.jTabbedPane1.setSelectedIndex(1);
            Campos.limpiarCampos(vistaEmpleats.jPanel2);
            vistaEmpleats.btnModificar.setVisible(false);
            vistaEmpleats.btnInsertar.setVisible(true);

        }
        
        /**
         * Si l'origen de l'esdeveniment és el botó Modificar, recollim el valor dels 
         * textBox, els assignem a l'empleat i 
         * l'enviem al mètode modificarEmpleat de consultesEmpleats
         * Si modifica correctament o no ens informa
         */
        
        if (me.getSource() == vistaEmpleats.btnModificar) {
            int id = Integer.parseInt(vistaEmpleats.txtId.getText());
            String nom = vistaEmpleats.txtNom.getText();
            String apellido = vistaEmpleats.txtCognoms.getText();
            String dni = vistaEmpleats.txtNif.getText();
            String contraseña = vistaEmpleats.txtContraseña.getText();
            Boolean administrador = vistaEmpleats.rbtnAdministrador.isSelected();

            empleat.setIdempleado(id);
            empleat.setNom(nom);
            empleat.setApellidos(apellido);
            empleat.setDni(dni);
            empleat.setCategoria("conductor");
            empleat.setEmpresaid(1);
            empleat.setContraseña(contraseña);
            empleat.setAdministrador(administrador);

            try {
                Boolean resposta = consulta.modificarEmpleat(empleat, token);
                System.out.println("");
                if (resposta) {
                    JOptionPane.showMessageDialog(null, "Modificat correctament");
                    consulta.carregaTaula(vistaEmpleats, token);

                } else {
                    JOptionPane.showMessageDialog(null, "No modificat correctament");

                }
            } catch (IOException ex) {
                Logger.getLogger(ctrlEmpleats.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        /**
         * Si l'origen de l'esdeveniment és el botó Salir, 
         * sortim de la pantalla de Empleats i tornem a la
         * d'Opcions
         */
        
        if (me.getSource() == vistaEmpleats.btnSalir) {
            vistaEmpleats.dispose();

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
