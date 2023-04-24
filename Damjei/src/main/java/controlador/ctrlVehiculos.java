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

import model.Vehicle;
import model.consultasVehicle;
import model.consultesEmpleats;
import vista.frmEmpleats;

import vista.frmVehicle;

/**
 *
 * @author JavierFernándezDíaz
 */
public class ctrlVehiculos implements ActionListener, MouseListener {

    private frmVehicle vistaVehicle;
    private consultasVehicle consulta;
    private Vehicle vehicle;
    private String token;

    public ctrlVehiculos(frmVehicle vista, consultasVehicle consulta, Vehicle vehicle, String token) throws IOException {
        this.vistaVehicle = vista;
        this.consulta = consulta;
        this.token = token;
        System.out.println(token);
        System.out.println(consulta);
        this.vehicle = vehicle;
        System.out.println(vehicle);
        consulta.carregaTaula(vista, token);
        vista.taulaVehicles.addMouseListener(this);
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
        if (me.getSource() == vistaVehicle.taulaVehicles) {
            int fila = vistaVehicle.taulaVehicles.getSelectedRow();
            String codigo = vistaVehicle.taulaVehicles.getValueAt(fila, 0).toString();
            vistaVehicle.txtId.setText(codigo);
            if (me.getClickCount() == 2) {
                vistaVehicle.jTabbedPane1.setSelectedIndex(1);
                vistaVehicle.btnModificar.setVisible(true);
                vistaVehicle.btnInsertar.setVisible(false);
                try {
                    consulta.carregaVehicle(Integer.parseInt(codigo), vistaVehicle);
                } catch (IOException ex) {
                    Logger.getLogger(ctrlEmpleats.class.getName()).log(Level.SEVERE, null, ex);
                }

            }

        }

        if (me.getSource() == vistaVehicle.btnInsertar) {
            System.out.println("Insertar");
            String matricula = vistaVehicle.txtMatricula.getText();
            String marca = vistaVehicle.txtMarca.getText();
            String model = vistaVehicle.txtModel.getText();
            Float kmactu = Float.valueOf(vistaVehicle.txtKmActu.getText());
            Float kmalta = Float.valueOf(vistaVehicle.txt_KmAlta.getText());
            String dataalta = vistaVehicle.txtDataAlta.getText();
            String databaixa = vistaVehicle.txtDataBaixa.getText();
            

            vehicle.setMatricula(matricula);
            vehicle.setMarca(marca);
            vehicle.setModelo(model);
            vehicle.setKilometros_actuales(kmactu);
            vehicle.setKilometros_alta(kmalta);
            vehicle.setFecha_alta(dataalta);
            vehicle.setFecha_baja(databaixa);
            vehicle.setConductorid(1);
            vehicle.setEmpresaid(1);

            try {
                Boolean resposta = consulta.insertarVehicle(vehicle, token);
                System.out.println("");
                if (resposta) {
                    JOptionPane.showMessageDialog(null, "Afegit correctament");
                    consulta.carregaTaula(vistaVehicle, token);

                } else {
                    JOptionPane.showMessageDialog(null, "No afegit correctament");

                }
            } catch (IOException ex) {
                Logger.getLogger(ctrlEmpleats.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        if (me.getSource() == vistaVehicle.btnBorrar) {
            
            int fila = vistaVehicle.taulaVehicles.getSelectedRow();
            String matricula= (vistaVehicle.taulaVehicles.getValueAt(fila, 3)).toString();

            vehicle.setMatricula(matricula);
            
            try {
                if (consulta.eliminarVehicle(vehicle, token)) {
                    vistaVehicle.jTabbedPane1.setSelectedIndex(0);
                    JOptionPane.showMessageDialog(null, "Borrado correctamente");
                    consulta.carregaTaula(vistaVehicle, token);

                } else {
                    JOptionPane.showMessageDialog(null, "No borrado");
                }
            } catch (IOException ex) {
                Logger.getLogger(ctrlEmpleats.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

        if (me.getSource() == vistaVehicle.btnNuevo) {
            vistaVehicle.jTabbedPane1.setSelectedIndex(1);
            Campos.limpiarCampos(vistaVehicle.jPanel2);
            vistaVehicle.btnModificar.setVisible(false);
            vistaVehicle.btnInsertar.setVisible(true);

        }

        if (me.getSource() == vistaVehicle.btnSalir) {
            vistaVehicle.dispose();

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


