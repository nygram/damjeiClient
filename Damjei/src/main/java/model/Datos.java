/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Ivimar
 */
public class Datos {
    
    private Empleats empleat;
    private String clase;
    //private String token;
    //private boolean correcte;
    //private boolean administrador;
    private int accio;

    public Datos() {
    }
 
  // getters y setters
    public Empleats getEmpleat() {
        return empleat;
    }

    public void setEmpleat(Empleats empleat) {
        this.empleat = empleat;
    }

    public int getAccio() {
        return accio;
    }

    public void setAccio(int accio) {
        this.accio = accio;
    }

    public String getClase() {
        return clase;
    }

    public void setClase(String clase) {
        this.clase = clase;
    }
  
}



