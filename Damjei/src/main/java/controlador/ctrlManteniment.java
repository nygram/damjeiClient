/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import Utils.Campos;
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
 *
 * @author Javi
 */
public class ctrlManteniment implements ActionListener, MouseListener {

    private frmManteniment vistaManteniment;
    private consultasManteniment consulta;
    private Mantenimiento mantenimiento;
    private String token;

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

    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

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
        if (me.getSource() == vistaManteniment.btnInsertar) {
            System.out.println("Insertar");
            String nom = vistaManteniment.txtNom.getText();
            Float km = null;
            if (Float.valueOf(vistaManteniment.txtKm.getText()) != null){
                km = Float.valueOf(vistaManteniment.txtKm.getText());
            }
            
            mantenimiento.setNombre(nom);
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

        if (me.getSource() == vistaManteniment.btnBorrar) {

            int fila = vistaManteniment.taulaMantenimiento.getSelectedRow();
            int id = (int) vistaManteniment.taulaMantenimiento.getValueAt(fila, 0);

            mantenimiento.setIdmantenimiento(id);

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
        if (me.getSource() == vistaManteniment.btnNuevo) {
            vistaManteniment.jTabbedPane1.setSelectedIndex(1);
            Campos.limpiarCampos(vistaManteniment.jPanel2);
            vistaManteniment.btnModificar.setVisible(false);
            vistaManteniment.btnInsertar.setVisible(true);

        }

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

