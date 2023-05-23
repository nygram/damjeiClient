
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
import model.Repostar;
import model.Revisiones;
import model.consultasRevisiones;
import model.consultesRepostatge;
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
public class RepostatgeTest {
    private ctrlLogin controlador;
    private Repostar repostar;
    private frmLogin vista;
    private Empleats empleat;
    private consultesRepostatge consulta;
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
        repostar = new Repostar();
        empleat = new Empleats();
        consulta = new consultesRepostatge();
        comunica = new Comunica();
        Encriptador hash = new Encriptador();
        empleat.setDni("40447212x");
        empleat.setContrasenya(hash.encriptarConSha256("0000"));
        objecte = comunica.enviaLogin(empleat);
        token = objecte.get("token").getAsString();

    }
    
    @Test
    @Order(1)
    public void testInsertar() throws IOException, KeyStoreException, FileNotFoundException, CertificateException, UnrecoverableKeyException, KeyManagementException, NoSuchAlgorithmException {

        repostar.setCombustibleid(1);
        repostar.setConductorid(3);
        repostar.setFecha_repostar("2023-01-01");
        repostar.setImporte_repostar(50f);
        repostar.setKilometros_repostar(23f);
        repostar.setLitros(24f);
        repostar.setVehiculoid(3);
        
        correcte = consulta.insertarRepostatge(repostar, token);
        assertTrue(correcte);

    }
/*
    @Test
    @Order(2)
    public void testModificar() throws IOException, KeyStoreException, FileNotFoundException, CertificateException, UnrecoverableKeyException, KeyManagementException, NoSuchAlgorithmException, InterruptedException {
        Thread.sleep(1000);
        manteniment.setNombre("escapaments");
        manteniment.setKilometros_mantenimiento(45.5f);
        manteniment.setFecha_mantenimiento("2001-03-03");

        correcte = consulta.modificarManteniment(manteniment, token);
        System.out.println("Correcte de modificar " + correcte);
        assertTrue(correcte);

    }

    @Test
    @Order(3)
    public void testEliminar() throws IOException, KeyStoreException, FileNotFoundException, CertificateException, UnrecoverableKeyException, KeyManagementException, NoSuchAlgorithmException, InterruptedException {
        Thread.sleep(2000);

        manteniment.setNombre("escapaments");

        correcte = consulta.eliminarManteniment(manteniment, token);
        assertTrue(correcte);

    }
 */   
    
}
