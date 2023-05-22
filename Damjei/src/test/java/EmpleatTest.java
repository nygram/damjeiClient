
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
import model.consultesEmpleats;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;
import vista.frmLogin;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author Javi
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class EmpleatTest {

    private ctrlLogin controlador;
    private Empleats empleat;
    private frmLogin vista;
    private consultesEmpleats consulta;
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
        empleat = new Empleats();
        consulta = new consultesEmpleats();
        comunica = new Comunica();
        Encriptador hash = new Encriptador();
        gson = new Gson();
        empleat.setDni("40447212x");
        empleat.setContrasenya(hash.encriptarConSha256("0000"));
        objecte = comunica.enviaLogin(empleat);
        token = objecte.get("token").getAsString();

    }

    @Test
    @Order(1)
    public void testInsertar() throws IOException, KeyStoreException, FileNotFoundException, CertificateException, UnrecoverableKeyException, KeyManagementException, NoSuchAlgorithmException {

        empleat.setNom("Cristina");
        empleat.setApellidos("Espigule");
        empleat.setDni("00000009f");
        empleat.setCategoria("rrhh");
        empleat.setEmpresaid(1);
        empleat.setContraseña("1234");
        empleat.setAdministrador(true);

        correcte = consulta.insertarEmpleat(empleat, token);
        assertTrue(correcte);

    }

    @Test
    @Order(2)
    public void testModificar() throws IOException, KeyStoreException, FileNotFoundException, CertificateException, UnrecoverableKeyException, KeyManagementException, NoSuchAlgorithmException {
        
        empleat.setDni("00000009f");
        empleat.setContrasenya("0987");

        correcte = consulta.modificarEmpleat(empleat, token);
        System.out.println("Correcte de modificar " + correcte);
        assertTrue(correcte);

    }

    @Test
    @Order(3)
    public void testEliminar() throws IOException, KeyStoreException, FileNotFoundException, CertificateException, UnrecoverableKeyException, KeyManagementException, NoSuchAlgorithmException{
        
        empleat.setDni("00000009f");

        correcte = consulta.eliminarEmpleat(empleat, token);
        assertTrue(correcte);

    }

}
