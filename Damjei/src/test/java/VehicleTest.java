
import com.Comunica;
import com.Encriptador;
import com.comDades;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import controlador.ctrlLogin;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import model.Empleats;
import model.Vehicle;
import model.consultasVehicle;
import model.consultesEmpleats;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Order;
import vista.frmLogin;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author JavierFernándezDíaz
 */
public class VehicleTest {
    
    private ctrlLogin controlador;
    private Vehicle vehicle;
    private Empleats empleat;
    private frmLogin vista;
    private consultasVehicle consulta;
    private JsonObject objecte;
    private String token;
    private Comunica comunica;
    private comDades com;
    private boolean correcte;
    private Gson gson;
    int port = 8180;
    String ip = "127.0.0.1";

    @Before
    public void setUp() throws IOException, KeyStoreException, FileNotFoundException, CertificateException, UnrecoverableKeyException, KeyManagementException, NoSuchAlgorithmException {

        vista = new frmLogin();
        vehicle = new Vehicle();
        consulta = new consultasVehicle();
        comunica = new Comunica();
        empleat = new Empleats();
        Encriptador hash = new Encriptador();
        empleat.setDni("40447212x");
        empleat.setContrasenya(hash.encriptarConSha256("0000"));
        objecte = comunica.enviaLogin(empleat);
        token = objecte.get("token").getAsString();

    }
    @Test
    @Order(1)
    public void testInsertar() throws IOException, KeyStoreException, FileNotFoundException, CertificateException, UnrecoverableKeyException, KeyManagementException, NoSuchAlgorithmException {

        vehicle.setMatricula("5555AAA");
            vehicle.setMarca("Nissan");
            vehicle.setModelo("Micar");
            vehicle.setKilometros_actuales(10000f);
            vehicle.setKilometros_alta(1000f);
            vehicle.setFecha_alta("2023-01-01");
            vehicle.setFecha_baja("2023-03-03");
            vehicle.setConductorid(3);
            vehicle.setEmpresaid(1);
        
        correcte = consulta.insertarVehicle(vehicle, token);
        assertTrue(correcte);

    }

    @Test
    @Order(2)
    public void testModificar() throws IOException, KeyStoreException, FileNotFoundException, CertificateException, UnrecoverableKeyException, KeyManagementException, NoSuchAlgorithmException, InterruptedException {
        
        Thread.sleep(1000);
        vehicle.setMatricula("5444FFF");
        vehicle.setKilometros_actuales(12000f);

        correcte = consulta.modificarVehicle(vehicle, token);
        assertTrue(correcte);

    }

    @Test
    @Order(3)
    public void testEliminar() throws IOException, KeyStoreException, FileNotFoundException, CertificateException, UnrecoverableKeyException, KeyManagementException, NoSuchAlgorithmException, InterruptedException {
        Thread.sleep(2000);

        vehicle.setMatricula("7777BGT");
        correcte = consulta.eliminarVehicle(vehicle, token);
        assertTrue(correcte);

    }
}
