package controlador;

import com.Comunica;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.Socket;
import java.sql.SQLException;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import model.Combustible;
import model.Empleats;
import model.Mantenimiento;
import model.Repostar;
import model.Vehicle;
import model.consultasManteniment;
import model.consultasVehicle;
import model.consultesCombustible;
import model.consultesEmpleats;
import model.consultesRepostatge;
import principal.Damjei;
import vista.frmCombustible;
import vista.frmEmpleats;
import vista.frmEmpleats;
import vista.frmLogin;
import vista.frmManteniment;
import vista.frmOpcions;
import vista.frmRepostatge;
import vista.frmVehicle;

/**
 * @author JavierFernándezDíaz Controlador de la part de Login. S'encarregar de
 * comunicar la vista amb el model i de la part lògica. Implmenta la interface
 * ActionListener
 *
 */
public class ctrlLogin implements ActionListener {

    private Socket socket;
    private ctrlEmpleats controlEmpleats;
    private ctrlVehiculos controlVehiculos;
    private ctrlManteniment controlManteniments;
    private ctrlCombustible controlCombustible;
    private ctrlRepostar controlRepostar;
    private consultesEmpleats consulta = new consultesEmpleats();
    private consultasVehicle consultasVehiculos = new consultasVehicle();
    private consultasManteniment consultaManteniment = new consultasManteniment();
    private consultesCombustible consultaCombustible = new consultesCombustible();
    private consultesRepostatge consultaRepostatge = new consultesRepostatge();
    private frmEmpleats vistaEmpleats = new frmEmpleats();
    private frmCombustible vistaCombustible = new frmCombustible();
    private frmLogin vistaLogin;
    private frmOpcions vistaOpcions;
    private frmVehicle vistaVehiculos = new frmVehicle();
    private frmManteniment vistaManteniment = new frmManteniment();
    private frmRepostatge vistaRepostatge = new frmRepostatge();
    private Empleats usuari;
    private Vehicle vehicle;
    private Mantenimiento manteniment;
    private Combustible combustible;
    private Repostar repostatge;
    private Comunica comunica = new Comunica();
    private JsonObject object;
    private boolean correcte = false;
    private boolean administrador = false;
    private String token;
    private int contador = 1;

    /**
     * Constructor de la classe ctrlLogin.
     *
     * @param vista es la vista que hem de carregar
     * @param usuari objecte de la classe Empleats
     */
    public ctrlLogin(frmLogin vista, Empleats usuari, Vehicle vehicle, Mantenimiento manteniment, Combustible combustible, Repostar repostatge) {
        this.vistaLogin = vista;
        this.usuari = usuari;
        this.vehicle = vehicle;
        this.manteniment = manteniment;
        this.combustible = combustible;
        this.repostatge = repostatge;
        vistaLogin.btnLogin.addActionListener(this);

        vistaOpcions = new frmOpcions();
        vistaOpcions.setVisible(false);
        vistaOpcions.setLocationRelativeTo(null);
        vistaOpcions.btnLogout.addActionListener(this);
        vistaOpcions.btnEmpleats.addActionListener(this);
        vistaOpcions.btnVehicles.addActionListener(this);
        vistaOpcions.btnManteniments.addActionListener(this);
        vistaOpcions.btnCombustible.addActionListener(this);
        vistaOpcions.btnRepostatge.addActionListener(this);

    }

    /**
     * Mètode a implementar de la interface ActionListener Segons l'origen de
     * l'esdeveniment s'executa un codi o un altre
     *
     * @param e esdeveniment que s'ha produït i que es capturat
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        /**
         * Si l'origen de l'esdevenimens es el botó de Login capturem el
         * contingut dels TextBox de Usuari i contrasenya i el enviem al
         * servidor a través de la classe Comunica fent servir el mètode
         * enviaLogin. El servidor ens torna un JsonObject que conté si la
         * calidació ha estat correcte, si l'usuari és administrador i el token
         * de la sessió
         */
        if (e.getSource() == vistaLogin.btnLogin) {
            usuari.setDni(vistaLogin.txtUsuari.getText());
            usuari.setContrasenya(String.valueOf(vistaLogin.txtPasswd.getPassword()));

            try {
                object = comunica.enviaLogin(usuari);
                correcte = object.get("correcte").getAsBoolean();
                administrador = object.get("administrador").getAsBoolean();
                token = object.get("token").getAsString();

                if (correcte) {
                    if (administrador) {
                        vistaLogin.setVisible(false);
                        vistaOpcions.setVisible(true);
                        vistaOpcions.btnEmpleats.setVisible(true);
                        vistaOpcions.txtToken.setText(token);
                    } else {
                        vistaLogin.setVisible(false);
                        vistaOpcions.setVisible(true);
                        vistaOpcions.btnEmpleats.setVisible(false);
                        vistaOpcions.txtToken.setText(token);
                    }
                } else {
                    if (contador < 3) {
                        JOptionPane.showMessageDialog(null, "Error al validar l'usuari. Et queden " + (3 - contador) + " intents");
                        contador++;
                    } else {
                        JOptionPane.showMessageDialog(null, "Ho has intentat masses vegades.");
                        vistaLogin.dispose();
                        System.exit(0);
                    }

                }

            } catch (IOException ex) {
                Logger.getLogger(ctrlLogin.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        /**
         * Si l'origen de l'esdeveniment és el botó empleats inicialitzem el
         * controlador de empleat passant com a paràmetres la vista, consultes,
         * empleat i el token per poder fer peticions al servidor
         */
        if (e.getSource() == vistaOpcions.btnEmpleats) {
            try {
                controlEmpleats = new ctrlEmpleats(vistaEmpleats, consulta, usuari, token);
                vistaEmpleats.setVisible(true);
                vistaEmpleats.txtToken.setText(token);
                vistaEmpleats.txtId.setVisible(false);
                vistaEmpleats.txtToken.setVisible(false);
            } catch (IOException ex) {
                Logger.getLogger(ctrlLogin.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

        /**
         * Si l'origen de l'esdeveniment és el botó vehicle inicialitzem el
         * controlador de vehicle passant com a paràmetres la vista, consultes,
         * vehicle i el token per poder fer peticions al servidor
         */
        if (e.getSource() == vistaOpcions.btnVehicles) {
            try {
                controlVehiculos = new ctrlVehiculos(vistaVehiculos, consultasVehiculos, vehicle, token);
                vistaVehiculos.setVisible(true);
                vistaVehiculos.txtToken.setText(token);
            } catch (IOException ex) {
                Logger.getLogger(ctrlLogin.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        /**
         * Si l'origen de l'esdeveniment és el botó manteniment inicialitzem el
         * controlador de manteniment passant com a paràmetres la vista,
         * consultes, manteniment i el token per poder fer peticions al servidor
         */

        if (e.getSource() == vistaOpcions.btnManteniments) {
            try {
                controlManteniments = new ctrlManteniment(vistaManteniment, consultaManteniment, manteniment, token);
                vistaManteniment.setVisible(true);
                vistaManteniment.txtToken.setText(token);
            } catch (IOException ex) {
                Logger.getLogger(ctrlLogin.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

        /**
         * Si l'origen de l'esdeveniment és el botó combustible inicialitzem el
         * controlador de manteniment passant com a paràmetres la vista,
         * consultes, manteniment i el token per poder fer peticions al servidor
         */
        if (e.getSource() == vistaOpcions.btnCombustible) {
            try {
                controlCombustible = new ctrlCombustible(vistaCombustible, consultaCombustible, combustible, token);
                vistaCombustible.setVisible(true);
                vistaCombustible.txtToken.setText(token);

                

            } catch (IOException ex) {
                Logger.getLogger(ctrlLogin.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

        if (e.getSource() == vistaOpcions.btnRepostatge) {
            try {
                controlRepostar = new ctrlRepostar(vistaRepostatge, consultaRepostatge, repostatge, token);
                vistaRepostatge.setVisible(true);
                vistaRepostatge.txtToken.setText(token);
                
                
                Vector<Vehicle> vectorVehicles = consultaRepostatge.mostrarVehicles(token);

                    DefaultComboBoxModel com = new DefaultComboBoxModel (vectorVehicles);
                    vistaRepostatge.cmbVehicles.setModel(com);

                
            } catch (IOException ex) {
                Logger.getLogger(ctrlLogin.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

        /**
         * Si l'origen de l'esdevenimens es el botó de Logout envia a través del
         * mètode enviaLogout al servidor el token per permetre a aquest
         * eliminar-lo de la seva llista
         *
         */
        if (e.getSource() == vistaOpcions.btnLogout) {
            try {
                comunica.enviaLogout(token);
            } catch (IOException ex) {
                Logger.getLogger(ctrlLogin.class.getName()).log(Level.SEVERE, null, ex);
            }
            System.exit(0);
        }

    }

}
