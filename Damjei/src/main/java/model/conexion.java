
package model;

import java.sql.Connection;
import java.sql.DriverManager;
import javax.swing.JOptionPane;

/**
 *
 * @author Javi
 */
    public class conexion {
    
    /**
     *
     */
    public static final String URL = "jdbc:postgresql://localhost:5432/M13";

    /**
     *
     */
    public static final String Usuari = "nygram";

    /**
     *
     */
    public static final String Contrasenya = "wolsimjonar";
    
    /**
     *
     * @return
     */
    public Connection getConnection(){
        Connection connexio = null;
        
        try{
            Class.forName("org.postgresql.Driver");
            connexio = (Connection) DriverManager.getConnection(URL, Usuari, Contrasenya);
            //JOptionPane.showMessageDialog(null, "Connexión correcta");
        }catch(Exception ex){
            System.err.println("Error, "+ex);
                 
        }
        return connexio;
    }
    
}
