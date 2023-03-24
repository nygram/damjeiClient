package com;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import model.Empleats;
import model.conexion;

/**
 * S'encarrega de fer les peticions que necesita el servidor a la base de dades
 * @author Javi
 */
public class ServerLogin extends conexion {

    PreparedStatement ps = null;
    ResultSet rs = null;

    /**
     *
     */
    public ServerLogin() {
    }

    /**
     * A partir d'un objecte JsonObject i del socket extreu la informació necessària 
     * i la busca a la base de dades l'usuari i la contrasenya
     * @param object Objecte de tipus Json
     * @param socket Socket de conexió del servidor
     * @return retorna un booleà depenent si ha trobar l'usuari i la contrasenya o no a la base de dades
     * @throws IOException
     * @throws SQLException
     */
    public boolean Login(JsonObject object, Socket socket) throws IOException, SQLException {

        Gson gson = new Gson();
        Empleats empleat = gson.fromJson(object.get("empleat"), Empleats.class);
        int accio = object.get("accio").getAsInt();
        String nom = empleat.getNom();
        String passw = empleat.getContrasenya();

        Connection conexio = getConnection();
        String sql = "SELECT COUNT(*) FROM empleados WHERE nombre = ? and contraseña = ?";
        ps = conexio.prepareStatement(sql);
        ps.setString(1, nom);
        ps.setString(2, passw);

        rs = ps.executeQuery();
        rs.next();
        int count = rs.getInt(1);
        System.out.println(count);
        rs.close();

        if (count > 0) {
            return true;
        } else {
            return false;
        }

    }

    /**
     * A partir d'un objecte JsonObject i del socket extreu la informació necessària 
     * i la busca a la base de dades si l'usuria és administrador
     * @param object Onjecte de tipus Json
     * @param socket Socket de conexió del servidor
     * @return Retorna un booleà segons l'usuari es o no administrador
     * @throws IOException
     * @throws SQLException
     */
    public boolean Admin(JsonObject object, Socket socket) throws IOException, SQLException {

        boolean admin = false;

        Gson gson = new Gson();
        Empleats empleat = gson.fromJson(object.get("empleat"), Empleats.class);
        int accio = object.get("accio").getAsInt();
        String nom = empleat.getNom();
        String passw = empleat.getContrasenya();

        Connection conexio = getConnection();
        String sql = "SELECT administrador FROM empleados WHERE nombre = ? and contraseña = ?";
        ps = conexio.prepareStatement(sql);
        ps.setString(1, nom);
        ps.setString(2, passw);
        
        

        rs = ps.executeQuery();
        if(rs.next()){
        admin = rs.getBoolean("administrador");
        return admin;
        }else{
            admin = false;
            return admin;
        }
    }

}
