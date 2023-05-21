/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Javi
 */
public class Revisiones {
    
    int idrevision;
    String fecha_revision;
    Float kilometros_revision;
    int vehiculoid;
    int mantenimientoid;
    Boolean estado_revision;

    public Revisiones() {
    }
    
    

    public int getIdrevision() {
        return idrevision;
    }

    public void setIdrevision(int idrevision) {
        this.idrevision = idrevision;
    }

    public String getFecha_revision() {
        return fecha_revision;
    }

    public void setFecha_revision(String fecha_revision) {
        this.fecha_revision = fecha_revision;
    }

    public Float getKilometros_revision() {
        return kilometros_revision;
    }

    public void setKilometros_revision(Float kilometros_revision) {
        this.kilometros_revision = kilometros_revision;
    }

    public int getVehiculoid() {
        return vehiculoid;
    }

    public void setVehiculoid(int vehiculoid) {
        this.vehiculoid = vehiculoid;
    }

    public int getMantenimientoid() {
        return mantenimientoid;
    }

    public void setMantenimientoid(int mantenimientoid) {
        this.mantenimientoid = mantenimientoid;
    }

    public Boolean getEstado_revision() {
        return estado_revision;
    }

    public void setEstado_revision(Boolean estado_revision) {
        this.estado_revision = estado_revision;
    }
    
    
    
}
