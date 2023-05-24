
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
import model.Mantenimiento;
import model.Revisiones;
import model.consultasManteniment;
import model.consultasRevisiones;
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
public class RevisioTest {
    private ctrlLogin controlador;
    private Revisiones revisio;
    private frmLogin vista;
    private Empleats empleat;
    private consultasRevisiones consulta;
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
        revisio = new Revisiones();
        empleat = new Empleats();
        consulta = new consultasRevisiones();
        comunica = new Comunica();
        Encriptador hash = new Encriptador();
        empleat.setDni("40447212x");
        empleat.setContrasenya(hash.encriptarConSha256("0000"));
        objecte = comunica.enviaLogin(empleat);
        token = objecte.get("token").getAsString();

    }
    
    @Test
    @Order(1)
    public void testModificar() throws IOException, KeyStoreException, FileNotFoundException, CertificateException, UnrecoverableKeyException, KeyManagementException, NoSuchAlgorithmException, InterruptedException {
        Thread.sleep(1000);
        
        revisio.setIdrevision(13);
        revisio.setEstado_revision(false);
       

        correcte = consulta.modificarRevisio(revisio, token);
        System.out.println("Correcte de modificar " + correcte);
        assertTrue(correcte);

    }

    @Test
    @Order(2)
    public void testEliminar() throws IOException, KeyStoreException, FileNotFoundException, CertificateException, UnrecoverableKeyException, KeyManagementException, NoSuchAlgorithmException, InterruptedException {
        Thread.sleep(2000);

        revisio.setIdrevision(13);

        correcte = consulta.eliminarRevisio(revisio, token);
        assertTrue(correcte);

    }
    
}
