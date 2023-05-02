
import com.Comunica;
import com.Server;
import com.google.gson.JsonObject;
import controlador.ctrlLogin;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashSet;
import model.Empleats;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import vista.frmLogin;

/**
 * Fa test del programa
 * @author Javi
 */
public class ClientTest {

    private ctrlLogin controlador;
    private Empleats empleat;
    private frmLogin vista;
    JsonObject objecte;
    private Comunica comunica;
    boolean correcte;
    boolean administrador;
    /**
     * Inicialitzem interface i servidor
     * @throws IOException
     * @throws SQLException 
     */
    @Before
    public void setUp() throws IOException {
        vista = new frmLogin();
        empleat = new Empleats();
        comunica = new Comunica();

    }
    /**
     * Valida amb un usuari i contrasenya que son correctes
     * @throws IOException 
     */
    @Test
    public void testLoginValid() throws IOException {
        empleat.setDni("40447212x");
        empleat.setContrasenya("0000");
        objecte = comunica.enviaLogin(empleat);
        correcte = objecte.get("correcte").getAsBoolean();
        assertTrue(correcte);

    }
    /**
     * Valida amb un usuari i contrasenya que no son correctes
     * @throws IOException 
     */
    @Test
    public void testLoginInvalid() throws IOException {
        empleat.setDni("098767534F");
        empleat.setContrasenya("2345");
        objecte = comunica.enviaLogin(empleat);
        correcte = objecte.get("correcte").getAsBoolean();
        assertFalse(correcte);

    }
    /**
     * Valida amb un usuari i contrasenya on l'usuari es administrador
     * @throws IOException 
     */
    @Test
    public void testAdminValid() throws IOException {
        empleat.setDni("40447212x");
        empleat.setContrasenya("0000");
        objecte = comunica.enviaLogin(empleat);
        administrador = objecte.get("administrador").getAsBoolean();
        assertTrue(administrador);

    }
    /**
     * Valida amb un usuari i contrasenya on l'usuari no es administrador
     * @throws IOException 
     */
    @Test
    public void testAdminInValid() throws IOException {
        empleat.setDni("12345678f");
        empleat.setContrasenya("1111");
        objecte = comunica.enviaLogin(empleat);
        administrador = objecte.get("administrador").getAsBoolean();
        assertFalse(administrador);

    }
}
