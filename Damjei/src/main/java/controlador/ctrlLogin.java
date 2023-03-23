package controlador;

import com.Comunica;
import com.google.gson.JsonObject;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import model.Empleats;
import vista.frmLogin;
import vista.frmOpcions;

/**
 *
 * @author JavierFernándezDíaz
 */
public class ctrlLogin implements ActionListener {

    private frmLogin vistaLogin;
    private frmOpcions vistaOpcions;
    private Empleats usuari;
    private Comunica comunica = new Comunica();
    private JsonObject object;
    private boolean correcte = false;
    private boolean administrador = false;
    private int token;
    private int contador = 0;

    public ctrlLogin(frmLogin vista, Empleats usuari) {
        this.vistaLogin = vista;
        this.usuari = usuari;
        vistaLogin.btnLogin.addActionListener(this);
        vistaOpcions = new frmOpcions();
        vistaOpcions.setVisible(false);
        vistaOpcions.setLocationRelativeTo(null);
        vistaOpcions.btnLogout.addActionListener(this);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == vistaLogin.btnLogin) {
            usuari.setNom(vistaLogin.txtUsuari.getText());
            usuari.setContrasenya(vistaLogin.txtPasswd.getText());

            try {
                object = comunica.enviaLogin(usuari);
                    correcte = object.get("correcte").getAsBoolean();
                    administrador = object.get("administrador").getAsBoolean();
                    token = object.get("token").getAsInt();
                
                if (correcte) {
                    if (administrador) {
                        vistaLogin.setVisible(false);
                        vistaOpcions.setVisible(true);
                        vistaOpcions.btnAdministracio.setVisible(true);
                        vistaOpcions.txtToken.setText(Integer.toString(token));
                    } else {
                        vistaLogin.setVisible(false);
                        vistaOpcions.setVisible(true);
                        vistaOpcions.btnAdministracio.setVisible(false);
                        vistaOpcions.txtToken.setText(Integer.toString(token));
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Error al validar l'usuari");
                    contador++;
                }

            } catch (IOException ex) {
                Logger.getLogger(ctrlLogin.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    
        if (e.getSource() == vistaOpcions.btnLogout){
            
            System.out.println("Logout");
            comunica.setToken(0);
            
            try {
                comunica.enviaLogout(Integer.parseInt((vistaOpcions.txtToken.getText())));
                
            } catch (IOException ex) {
                Logger.getLogger(ctrlLogin.class.getName()).log(Level.SEVERE, null, ex);
            }
           
          
            
        }

    }

}
