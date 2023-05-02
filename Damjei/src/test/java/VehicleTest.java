
import com.Comunica;
import com.comDades;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import controlador.ctrlLogin;
import java.io.IOException;
import model.Empleats;
import model.Vehicle;
import model.consultasVehicle;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import vista.frmLogin;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author Javi
 */
@TestMethodOrder(OrderAnnotation.class)
public class VehicleTest {

    private ctrlLogin controlador;
    private Vehicle vehicle;
    private frmLogin vista;
    private Empleats empleat;
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
    public void setUp() throws IOException {
        vista = new frmLogin();
        vehicle = new Vehicle();
        empleat = new Empleats();
        consulta = new consultasVehicle();
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

        vehicle.setMatricula("7777BGT");
        vehicle.setMarca("Opel");
        vehicle.setModelo("Fiesta");
        vehicle.setKilometros_actuales(123f);
        vehicle.setKilometros_alta(1234f);
        vehicle.setFecha_alta("2021-03-20");
        vehicle.setFecha_baja("2021-05-26");
        vehicle.setConductorid(1);
        vehicle.setEmpresaid(1);

        correcte = consulta.insertarVehicle(vehicle, token);
        assertTrue(correcte);

    }
    

    @Test
    @Order(2)
    public void testModificar() throws IOException, InterruptedException {
        Thread.sleep(1000);
        vehicle.setMatricula("7777BGT");
        vehicle.setMarca("Opel");
        vehicle.setModelo("Fiesta");
        vehicle.setKilometros_actuales(123000f);
        vehicle.setKilometros_alta(123400f);
        vehicle.setFecha_alta("2022-03-20");
        vehicle.setFecha_baja("2029-05-26");
        vehicle.setConductorid(1);
        vehicle.setEmpresaid(1);;

        correcte = consulta.modificarVehicle(vehicle, token);
        System.out.println("Correcte de modificar " + correcte);
        assertTrue(correcte);

    }

    @Test
    @Order(3)
    public void testEliminar() throws IOException, InterruptedException {
        Thread.sleep(2000);
        vehicle.setMatricula("7777BGT");

        correcte = consulta.eliminarVehicle(vehicle, token);
        assertTrue(correcte);

    }


}
