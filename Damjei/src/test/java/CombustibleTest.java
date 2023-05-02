import com.Comunica;
import com.comDades;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import controlador.ctrlLogin;
import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import model.Combustible;
import model.Empleats;
import model.consultesCombustible;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.runners.MethodSorters;
import vista.frmLogin;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Javi
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CombustibleTest {
    private ctrlLogin controlador;
    private Combustible combustible;
    private frmLogin vista;
    private Empleats empleat;
    private consultesCombustible consulta;
    private JsonObject objecte;
    private String token;
    private Comunica comunica;
    private comDades com;
    private boolean correcte;
    private Gson gson;
    int port = 8180;
    String ip = "127.0.0.1";

    @Before
    public void setUp() throws IOException {
        vista = new frmLogin();
        combustible = new Combustible();
        empleat = new Empleats();
        consulta = new consultesCombustible();
        comunica = new Comunica();
        gson = new Gson();
        empleat.setDni("40447212x");
        empleat.setContrasenya("0000");
        objecte = comunica.enviaLogin(empleat);
        token = objecte.get("token").getAsString();

    }

    @Test
    @Order(1)
    public void testInsertar() throws IOException {
        combustible.setNombre("GLP+");
        combustible.setPrecio(23.9f);
        
        correcte = consulta.insertarCombustible(combustible, token);
        assertTrue(correcte);

    }
    
    @Test
    @Order(2)
    public void testModificar() throws IOException {
        combustible.setNombre("GLP+");
        combustible.setPrecio(45.5f);
        correcte = consulta.modificarCombustible(combustible, token);
        System.out.println("Correcte de modificar " + correcte);
        assertTrue(correcte);
    }


    @Test
    @Order(3)
    public void testEliminar() throws IOException {
        combustible.setNombre("GLP+");

        correcte = consulta.eliminarCombustible(combustible, token);
        assertTrue(correcte);

    }

}
