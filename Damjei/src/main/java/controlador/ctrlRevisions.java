/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Combustible;
import model.Revisiones;
import model.consultasRevisiones;
import model.consultesCombustible;
import vista.frmCombustible;
import vista.frmRevisions;

/**
 *
 * @author Javi
 */
public class ctrlRevisions implements ActionListener, MouseListener{
    
    private frmRevisions vistaRevisions;
    private consultasRevisiones consulta;
    private Revisiones revisions;
    private String token;
    
    public ctrlRevisions(frmRevisions vista, consultasRevisiones consulta, Revisiones revisio, String token) throws IOException, KeyStoreException, FileNotFoundException, CertificateException, UnrecoverableKeyException, KeyManagementException, NoSuchAlgorithmException{
        this.vistaRevisions = vista;
        this.consulta = consulta;
        this.token = token;
        this.revisions = revisio;
        consulta.carregaTaula(vista, token);
        vista.taulaRevisions.addMouseListener(this);
        vista.btnInsertar.addMouseListener(this);
        vista.btnBorrar.addMouseListener(this);
        vista.btnNuevo.addMouseListener(this);
        vista.btnSalir.addMouseListener(this);
        vista.btnModificar.addMouseListener(this);
        vista.btnSalir.addActionListener(this);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == vistaRevisions.btnSalir) {
            vistaRevisions.dispose();

        }
    }

    @Override
    public void mouseClicked(MouseEvent me) {
        if (me.getSource() == vistaRevisions.taulaRevisions) {
            int fila = vistaRevisions.taulaRevisions.getSelectedRow();
            String codigo = vistaRevisions.taulaRevisions.getValueAt(fila, 0).toString();
            vistaRevisions.txtId.setText(codigo);
            if (me.getClickCount() == 2) {
                vistaRevisions.jTabbedPane1.setSelectedIndex(1);
                vistaRevisions.btnModificar.setVisible(true);
                vistaRevisions.btnInsertar.setVisible(false);
                try {
                    consulta.carregaRevisio(Integer.parseInt(codigo), vistaRevisions);
                } catch (IOException ex) {
                    Logger.getLogger(ctrlEmpleats.class.getName()).log(Level.SEVERE, null, ex);
                } catch (KeyStoreException ex) {
                    Logger.getLogger(ctrlCombustible.class.getName()).log(Level.SEVERE, null, ex);
                }  catch (CertificateException ex) {
                    Logger.getLogger(ctrlCombustible.class.getName()).log(Level.SEVERE, null, ex);
                } catch (UnrecoverableKeyException ex) {
                    Logger.getLogger(ctrlCombustible.class.getName()).log(Level.SEVERE, null, ex);
                } catch (KeyManagementException ex) {
                    Logger.getLogger(ctrlCombustible.class.getName()).log(Level.SEVERE, null, ex);
                } catch (NoSuchAlgorithmException ex) {
                    Logger.getLogger(ctrlCombustible.class.getName()).log(Level.SEVERE, null, ex);
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
    
}
