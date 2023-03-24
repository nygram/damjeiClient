package controlador;

import com.Comunica;
import com.google.gson.JsonObject;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.Socket;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import model.Empleats;
import principal.Damjei;
import vista.frmLogin;
import vista.frmOpcions;

/**
 * @author JavierFernándezDíaz
 * Controlador de la part de Login. S'encarregar de comunicar la vista amb el model
 * i de la part lògica. Implmenta la interface ActionListener 
 * 
 */
public class ctrlLogin implements ActionListener {

    private Socket socket;
    private frmLogin vistaLogin;
    private frmOpcions vistaOpcions;
    private Empleats usuari;
    private Comunica comunica = new Comunica();
    private JsonObject object;
    private boolean correcte = false;
    private boolean administrador = false;
    private int token;
    private int contador = 1;

/**
 * Constructor de la classe ctrlLogin. 
 * @param vista es la vista que hem de carregar
 * @param usuari objecte de la classe Empleats
 */
    public ctrlLogin(frmLogin vista, Empleats usuari) {
        this.vistaLogin = vista;
        this.usuari = usuari;
        vistaLogin.btnLogin.addActionListener(this);
        vistaOpcions = new frmOpcions();
        vistaOpcions.setVisible(false);
        vistaOpcions.setLocationRelativeTo(null);
        vistaOpcions.btnLogout.addActionListener(this);

    }
    /**
     * Mètode a implementar de la interface ActionListener 
     * Segons l'origen de l'esdeveniment s'executa un codi o un altre
     * @param e esdeveniment que s'ha produït i que es capturat
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        /**
         * Si l'origen de l'esdevenimens es el botó de Login
         * capturem el contingut dels TextBox de Usuari i contrasenya
         * i el enviem al servidor a través de la classe Comunica 
         * fent servir el mètode enviaLogin. El servidor ens torna un JsonObject
         * que conté si la calidació ha estat correcte, si l'usuari és administrador
         * i el token de la sessió
         */
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
                    if(contador < 3){
                       JOptionPane.showMessageDialog(null, "Error al validar l'usuari. Et queden "+ (3-contador) + " intents");
                        contador++; 
                    }else{
                       JOptionPane.showMessageDialog(null, "Ho has intentat masses vegades.");
                       vistaLogin.dispose();
                       System.exit(0); 
                    }
                    
                }

            } catch (IOException ex) {
                Logger.getLogger(ctrlLogin.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    
        if (e.getSource() == vistaOpcions.btnLogout){
            
            comunica.setToken(0);
            vistaOpcions.dispose();
            vistaLogin.dispose();
            System.exit(0);
            
            
            try {
                Damjei.main(null);
            } catch (IOException ex) {
                Logger.getLogger(ctrlLogin.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(ctrlLogin.class.getName()).log(Level.SEVERE, null, ex);
            }
                       
            try {
                comunica.enviaLogout(Integer.parseInt((vistaOpcions.txtToken.getText())));
                
            } catch (IOException ex) {
                Logger.getLogger(ctrlLogin.class.getName()).log(Level.SEVERE, null, ex);
            }
           
          
            
        }

    }

}
