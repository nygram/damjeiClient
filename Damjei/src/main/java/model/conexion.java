
package model;

import java.sql.Connection;
import java.sql.DriverManager;
import javax.swing.JOptionPane;

/**
 * Mètode per crear una conexió amb la base de dades
 * Creem un objecte de tipus Connextion que estableix
 * conexió amb el servidor postgres fent servir els paràmetres URL, Usuari, Contrasenya
 * @author Javi
 */
    public class conexion {
 
    public static final String URL = "jdbc:postgresql://localhost:5432/M13";
    public static final String Usuari = "nygram";
    public static final String Contrasenya = "wolsimjonar";
    

    public Connection getConnection(){
        Connection connexio = null;
        
        try{
            Class.forName("org.postgresql.Driver");
            connexio = (Connection) DriverManager.getConnection(URL, Usuari, Contrasenya);
        }catch(Exception ex){
            System.err.println("Error, "+ex);
                 
        }
        return connexio;
    }
    
}
