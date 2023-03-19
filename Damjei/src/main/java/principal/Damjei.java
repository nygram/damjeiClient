/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package principal;

import com.Server;
import controlador.ctrlLogin;
import java.io.IOException;
import model.Empleats;
import vista.frmLogin;

/**
 *
 * @author JavierFernándezDíaz
 */
public class Damjei {

    public static void main(String[] args) throws IOException {
        System.out.println("Hello World!");
        Server server = new Server();
        frmLogin vista = new frmLogin();
        Empleats empleat = new Empleats();
        ctrlLogin ctrl = new ctrlLogin(vista, empleat);
        vista.setVisible(true);
         server.iniciar();
    }
}
