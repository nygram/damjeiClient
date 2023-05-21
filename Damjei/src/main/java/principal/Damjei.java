/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package principal;

import com.Server;
import com.formdev.flatlaf.FlatLightLaf;
import controlador.ctrlLogin;
import java.awt.Color;
import java.io.IOException;
import java.sql.SQLException;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import model.Combustible;
import model.Empleats;
import model.Mantenimiento;
import model.Repostar;
import model.Revisiones;
import model.Vehicle;
import vista.frmLogin;

/**
 * @author JavierFernándezDíaz Classe principal que inicia el servidor simulat,
 * la vista on es fa Login, el controlador d'aquesta vista passant els
 * paràmetres. Fa visible la vista i la col·loca en pantalla
 *
 */
public class Damjei {

    /**
     * Mètode per arrencar el programa on inicialitzem el servidor de probes la
     * vista inicial del programa i l'empleat per poder, amb el seu constructor,
     * iniciar el controlador de login. Fem visible la vista i la col·loquem
     *
     * @param args
     * @throws IOException captura errors E/S
     * @throws SQLException captura errors SQL
     */
    public static void main(String[] args) throws IOException, SQLException, UnsupportedLookAndFeelException {
        //Server server = new Server();
        UIManager.setLookAndFeel(new FlatLightLaf());
        UIManager.put("Button.arc", 999);
        UIManager.put("TabbedPane.selectedBackground", Color.LIGHT_GRAY);
        UIManager.put("TabbedPane.selectedForeground", Color.BLUE);

        frmLogin vista = new frmLogin();
        Empleats empleat = new Empleats();
        Vehicle vehicle = new Vehicle();
        Mantenimiento manteniment = new Mantenimiento();
        Combustible combustible = new Combustible();
        Revisiones revisio = new Revisiones();
        Repostar repostar = new Repostar();
        ctrlLogin ctrl = new ctrlLogin(vista, empleat, vehicle, manteniment, combustible, repostar, revisio);
        vista.setVisible(true);
        vista.setLocationRelativeTo(null);
        //server.iniciar();
    }
}
