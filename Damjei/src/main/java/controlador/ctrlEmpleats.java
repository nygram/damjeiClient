/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

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
import vista.frmOpcions;

/**
 *
 * @author Javi
 */
public class ctrlEmpleats implements ActionListener, MouseListener, WindowListener, KeyListener, ItemListener {

    private frmEmpleats vistaEmpleats;
    private consultesEmpleats consulta;
    private Empleats empleat;
    private String token;
    private String String;

    public ctrlEmpleats(frmEmpleats vista, consultesEmpleats consulta, Empleats empleat, String token) throws IOException {
        this.vistaEmpleats = vista;
        this.consulta = consulta;
        this.token = token;
        System.out.println(token);
        System.out.println(consulta);
        this.empleat = empleat;
        System.out.println(empleat);
        consulta.carregaTaula(vista, token);
        vista.taulaEmpleats.addMouseListener(this);
        vista.btnInsertar.addMouseListener(this);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == vistaEmpleats.btnBorrar) {

            empleat.setIdempleado(Integer.parseInt(vistaEmpleats.txtId.getText()));

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

    }

    @Override
    public void mouseClicked(MouseEvent me) {
        if (me.getSource() == vistaEmpleats.taulaEmpleats) {
            int fila = vistaEmpleats.taulaEmpleats.getSelectedRow();
            String codigo = vistaEmpleats.taulaEmpleats.getValueAt(fila, 0).toString();
            if (me.getClickCount() == 2) {
                vistaEmpleats.jTabbedPane1.setSelectedIndex(1);
                try {
                    consulta.carregaEmpleat(Integer.parseInt(codigo), vistaEmpleats);
                } catch (IOException ex) {
                    Logger.getLogger(ctrlEmpleats.class.getName()).log(Level.SEVERE, null, ex);
                }

            }

        }
        if (me.getSource() == vistaEmpleats.btnInsertar) {
            System.out.println("Insertar");
            String nom = vistaEmpleats.txtNom.getText();
            String apellido = vistaEmpleats.txtCognoms.getText();
            String dni = vistaEmpleats.txtNif.getText();
            String contrasenya = vistaEmpleats.txtContraseña.getText();
            Boolean administrador = vistaEmpleats.rbtnAdministrador.isSelected();

            empleat.setNom(nom);
            empleat.setApellidos(apellido);
            empleat.setDni(dni);
            empleat.setCategoria("conductor");
            empleat.setId_empresa(1);
            empleat.setContrasenya(contrasenya);
            empleat.setAdministrador(administrador);

            try {
                Boolean resposta = consulta.insertarEmpleat(empleat, token);
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
        if (me.getSource() == vistaEmpleats.taulaEmpleats) {
            int fila = vistaEmpleats.taulaEmpleats.getSelectedRow();
            String codigo = vistaEmpleats.taulaEmpleats.getValueAt(fila, 0).toString();
            vistaEmpleats.txtId.setText(codigo);
            if (me.getClickCount() == 2) {
                vistaEmpleats.jTabbedPane1.setSelectedIndex(1);
                try {
                    consulta.carregaEmpleat(Integer.parseInt(codigo), vistaEmpleats);
                } catch (IOException ex) {
                    Logger.getLogger(ctrlEmpleats.class.getName()).log(Level.SEVERE, null, ex);
                }

            }

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

    @Override
    public void windowOpened(WindowEvent e) {
    }

    @Override
    public void windowClosing(WindowEvent e) {
    }

    @Override
    public void windowClosed(WindowEvent e) {
    }

    @Override
    public void windowIconified(WindowEvent e) {
    }

    @Override
    public void windowDeiconified(WindowEvent e) {
    }

    @Override
    public void windowActivated(WindowEvent e) {
    }

    @Override
    public void windowDeactivated(WindowEvent e) {
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
    }

}
