/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Empleats;
import model.consultesEmpleats;
import vista.frmEmpleats;
import vista.frmOpcions;

/**
 *
 * @author Javi
 */
public class ctrlEmpleats implements ActionListener, MouseListener {

    private frmEmpleats vistaEmpleats;
    private consultesEmpleats consulta;
    private Empleats empleat;
    private String token;

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

    }

    @Override
    public void mouseClicked(MouseEvent me) {
        if (me.getSource() == vistaEmpleats.taulaEmpleats) {
            int fila = vistaEmpleats.taulaEmpleats.getSelectedRow();
            String codigo = vistaEmpleats.taulaEmpleats.getValueAt(fila, 0).toString();
            if (me.getClickCount() == 2) {
                vistaEmpleats.jTabbedPane1.setSelectedIndex(1);
                consulta.carregaEmpleat(Integer.parseInt(codigo), vistaEmpleats);

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
                consulta.insertarEmpleat(empleat, token);
            } catch (IOException ex) {
                Logger.getLogger(ctrlEmpleats.class.getName()).log(Level.SEVERE, null, ex);
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

}
