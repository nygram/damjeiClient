package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;
import java.util.Set;
import model.Empleats;
import vista.frmLogin;

/**
 *
 * @author JavierFernándezDíaz
 */
public class ctrlLogin implements ActionListener{
    
    private frmLogin vista;
    private Empleats usuari;
    
   public ctrlLogin (frmLogin vista, Empleats usuari){
       this.vista = vista;
       this.usuari = usuari;
       vista.btnLogin.addActionListener(this);
       
   }

    @Override
    public void actionPerformed(ActionEvent e) {
        usuari.setNom(vista.txtUsuari.getText());
        usuari.setContrasenya(vista.txtUsuari.getText());
        
        
        
    }
    
    
}
