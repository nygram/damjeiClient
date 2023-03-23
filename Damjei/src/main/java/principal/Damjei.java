/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package principal;

import com.Server;
import controlador.ctrlLogin;
import java.io.IOException;
import java.sql.SQLException;
import model.Empleats;
import vista.frmLogin;

/**
 * @author JavierFernándezDíaz
 * Classe principal que inicia el servidor simulat, la vista on es fa Login,
 * el controlador d'aquesta vista passant els paràmetres.
 * Fa visible la vista i la col·loca en pantalla
 * 
 */
public class Damjei {

    /**
     *
     * @param args
     * @throws IOException
     * @throws SQLException
     */
    public static void main(String[] args) throws IOException, SQLException {
        Server server = new Server();
        frmLogin vista = new frmLogin();
        Empleats empleat = new Empleats();
        ctrlLogin ctrl = new ctrlLogin(vista, empleat);
        vista.setVisible(true);
        vista.setLocationRelativeTo(null);
        server.iniciar();
    }
}
