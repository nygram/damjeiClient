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
import model.Repostar;
import model.consultesRepostatge;
import vista.frmRepostatge;

/**
 *
 * @author Javi
 */
public class ctrlRepostar implements MouseListener, ActionListener {

    private frmRepostatge vistaRepostatge;
    private consultesRepostatge consulta;
    private Repostar repostatge;
    private String token;
    boolean combo = false;

    /**
     * Constructor del controlador que s'inicialitza amb la vista (frmVehicle),
     * consulta (consultesVehicles), vehicle (Vehicle) i el token. Afegeix
     * Listeners als botons i la taula.
     *
     * @param vista. Objecte de frmVehicle
     * @param consulta. Objecte de consultesVehicles
     * @param vehicle. Objecte vehicle
     * @param token. Token rebut del servidor
     * @throws IOException
     */
    public ctrlRepostar(frmRepostatge vista, consultesRepostatge consulta, Repostar repostatge, String token) throws IOException {
        this.vistaRepostatge = vista;
        this.consulta = consulta;
        this.token = token;
        System.out.println(token);
        System.out.println(consulta);
        this.repostatge = repostatge;
        System.out.println(repostatge);
        vista.cmbVehicles.addMouseListener(this);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (combo == false) {
            if (e.getSource() == vistaRepostatge.cmbVehicles) {
                try {
                    consulta.carregaComboVehicle(vistaRepostatge, token);
                    combo = true;
                } catch (IOException ex) {
                    Logger.getLogger(ctrlRepostar.class.getName()).log(Level.SEVERE, null, ex);
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
    public void actionPerformed(ActionEvent e) {
        
    }

}
