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
import javax.swing.JOptionPane;
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
public class ctrlRevisions implements ActionListener, MouseListener {

    private frmRevisions vistaRevisions;
    private consultasRevisiones consulta;
    private Revisiones revisions;
    private String token;

    public ctrlRevisions(frmRevisions vista, consultasRevisiones consulta, Revisiones revisio, String token) throws IOException, KeyStoreException, FileNotFoundException, CertificateException, UnrecoverableKeyException, KeyManagementException, NoSuchAlgorithmException {
        this.vistaRevisions = vista;
        this.consulta = consulta;
        this.token = token;
        this.revisions = revisio;
        consulta.carregaTaula(vista, token);
        vista.taulaRevisions.addMouseListener(this);
        vista.btnInsertar.addMouseListener(this);
        vista.btnBorrar.addActionListener(this);
        vista.btnNuevo.addMouseListener(this);
        vista.btnSalir.addMouseListener(this);
        vista.btnModificar.addActionListener(this);
        vista.btnSalir.addActionListener(this);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == vistaRevisions.btnSalir) {
            vistaRevisions.dispose();

        }

        if (e.getSource() == vistaRevisions.btnBorrar) {
            int id = Integer.parseInt(vistaRevisions.txtId.getText());

            revisions.setIdrevision(id);

            try {
                if (consulta.eliminarRevisio(revisions, token)) {
                    vistaRevisions.jTabbedPane1.setSelectedIndex(0);
                    JOptionPane.showMessageDialog(null, "Borrado correctamente");
                    consulta.carregaTaula(vistaRevisions, token);

                } else {
                    JOptionPane.showMessageDialog(null, "No borrado");
                }
            } catch (IOException ex) {
                Logger.getLogger(ctrlEmpleats.class.getName()).log(Level.SEVERE, null, ex);
            } catch (KeyStoreException ex) {
                Logger.getLogger(ctrlRevisions.class.getName()).log(Level.SEVERE, null, ex);
            } catch (CertificateException ex) {
                Logger.getLogger(ctrlRevisions.class.getName()).log(Level.SEVERE, null, ex);
            } catch (UnrecoverableKeyException ex) {
                Logger.getLogger(ctrlRevisions.class.getName()).log(Level.SEVERE, null, ex);
            } catch (KeyManagementException ex) {
                Logger.getLogger(ctrlRevisions.class.getName()).log(Level.SEVERE, null, ex);
            } catch (NoSuchAlgorithmException ex) {
                Logger.getLogger(ctrlRevisions.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        if (e.getSource() == vistaRevisions.btnModificar) {
            System.out.println("revisions");

            Boolean estat = vistaRevisions.rbtEstat.isSelected();

            int id = Integer.parseInt(vistaRevisions.txtId.getText());
            revisions.setIdrevision(id);
            revisions.setEstado_revision(estat);
            
                
            

            try {
                Boolean resposta = consulta.modificarRevisio(revisions, token);

                if (resposta) {
                    JOptionPane.showMessageDialog(null, "Modificat correctament");
                    consulta.carregaTaula(vistaRevisions, token);

                } else {
                    JOptionPane.showMessageDialog(null, "No modificat");

                }

            } catch (IOException ex) {
                Logger.getLogger(ctrlRevisions.class.getName()).log(Level.SEVERE, null, ex);
            } catch (KeyStoreException ex) {
                Logger.getLogger(ctrlRevisions.class.getName()).log(Level.SEVERE, null, ex);
            } catch (CertificateException ex) {
                Logger.getLogger(ctrlRevisions.class.getName()).log(Level.SEVERE, null, ex);
            } catch (UnrecoverableKeyException ex) {
                Logger.getLogger(ctrlRevisions.class.getName()).log(Level.SEVERE, null, ex);
            } catch (KeyManagementException ex) {
                Logger.getLogger(ctrlRevisions.class.getName()).log(Level.SEVERE, null, ex);
            } catch (NoSuchAlgorithmException ex) {
                Logger.getLogger(ctrlRevisions.class.getName()).log(Level.SEVERE, null, ex);
            }

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
                } catch (CertificateException ex) {
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
