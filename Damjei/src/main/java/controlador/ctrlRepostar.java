/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import model.Repostar;
import model.consultesRepostatge;
import org.postgresql.core.Utils;
import vista.frmRepostatge;
import Utils.Camps;
import java.io.FileNotFoundException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

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
    public ctrlRepostar(frmRepostatge vista, consultesRepostatge consulta, Repostar repostatge, String token) throws IOException, KeyStoreException, FileNotFoundException, CertificateException, UnrecoverableKeyException, KeyManagementException, NoSuchAlgorithmException {
        this.vistaRepostatge = vista;
        this.consulta = consulta;
        this.token = token;
        this.repostatge = repostatge;
        consulta.carregaTaula(vista, token);
        vista.cmbVehicles.addMouseListener(this);
        vista.btnRegistrar.addActionListener(this);
        vista.btnSalir.addActionListener(this);
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e
    ) {
    }

    @Override
    public void mouseEntered(MouseEvent e
    ) {
    }

    @Override
    public void mouseExited(MouseEvent e
    ) {
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == vistaRepostatge.cmbVehicles) {
            String matricula = (String) vistaRepostatge.cmbVehicles.getSelectedItem();
        }

        if (e.getSource() == vistaRepostatge.btnRegistrar) {

            Camps camp = new Camps();

            int idVehicle = Integer.parseInt(vistaRepostatge.txtId.getText());
            String data = vistaRepostatge.txtDataActual.getText();
            float imp = Float.parseFloat(vistaRepostatge.txtImport.getText());
            float km = Float.parseFloat(vistaRepostatge.txtKmActuals.getText());
            int idCombustible = Integer.parseInt(vistaRepostatge.txtCombustibleId.getText());
            int idConductor = Integer.parseInt(vistaRepostatge.txtConductorId.getText());
            float litres = Float.parseFloat(vistaRepostatge.txtLitres.getText());

            repostatge.setVehiculoid(idVehicle);
            repostatge.setFecha_repostar(data);
            repostatge.setImporte_repostar(imp);
            repostatge.setKilometros_repostar(km);
            repostatge.setCombustibleid(idCombustible);
            repostatge.setConductorid(idConductor);
            repostatge.setLitros(litres);

            try {
                Boolean resposta = consulta.insertarRepostatge(repostatge, token);
                if (resposta) {
                    JOptionPane.showMessageDialog(null, "Afegit correctament");
                    camp.netejaCamps(vistaRepostatge.jPanel1);
                    consulta.carregaTaula(vistaRepostatge, token);
                    vistaRepostatge.jTabbedPane1.setSelectedIndex(1);

                } else {
                    JOptionPane.showMessageDialog(null, "No afegit correctament");
                    consulta.carregaTaula(vistaRepostatge, token);

                }
            } catch (IOException ex) {
                Logger.getLogger(ctrlEmpleats.class.getName()).log(Level.SEVERE, null, ex);
            } catch (KeyStoreException ex) {
                Logger.getLogger(ctrlRepostar.class.getName()).log(Level.SEVERE, null, ex);
            } catch (CertificateException ex) {
                Logger.getLogger(ctrlRepostar.class.getName()).log(Level.SEVERE, null, ex);
            } catch (UnrecoverableKeyException ex) {
                Logger.getLogger(ctrlRepostar.class.getName()).log(Level.SEVERE, null, ex);
            } catch (KeyManagementException ex) {
                Logger.getLogger(ctrlRepostar.class.getName()).log(Level.SEVERE, null, ex);
            } catch (NoSuchAlgorithmException ex) {
                Logger.getLogger(ctrlRepostar.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (e.getSource() == vistaRepostatge.btnSalir) {
            vistaRepostatge.dispose();

        }

    }

}
