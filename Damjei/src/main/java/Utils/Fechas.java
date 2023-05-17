package Utils;

import java.text.SimpleDateFormat;
import java.sql.Date;

public class Fechas {

    public static Date dameFecha(Date data) {

        long date = data.getTime(); //guardamos en un long
        java.sql.Date fecha = new java.sql.Date(date);// pasamos al formato del sql 
        System.out.println("fecha es " + fecha);
        return fecha;

    }

    public static Date donaDataActual() {
        
        long millis = System.currentTimeMillis();
        java.sql.Date ahora = new java.sql.Date(millis);
        return ahora;
    }

    
}
