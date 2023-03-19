package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.ConsultesEmpleats;
import model.Empleats;
import vista.frmLogin;

/**
 *
 * @author JavierFernándezDíaz
 */
public class ctrlLogin implements ActionListener{
    
    private frmLogin vista;
    private Empleats usuari;
    private ConsultesEmpleats empleats = new ConsultesEmpleats();
    
   public ctrlLogin (frmLogin vista, Empleats usuari){
       this.vista = vista;
       this.usuari = usuari;
       vista.btnLogin.addActionListener(this);
       
   }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == vista.btnLogin){
        usuari.setNom(vista.txtUsuari.getText());
        usuari.setContrasenya(vista.txtPasswd.getText());
            System.out.println("hola");
        
        try {
            empleats.enviaLogin(usuari);
        } catch (IOException ex) {
            Logger.getLogger(ctrlLogin.class.getName()).log(Level.SEVERE, null, ex);
        }
        }    
        
        
    }
    
    
}
