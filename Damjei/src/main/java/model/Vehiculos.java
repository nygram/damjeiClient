/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.util.Date;

/**
 *
 * @author JavierFernándezDíaz
 */
public class Vehiculos {
    
    int idvehiculos;
    String marca;
    String modelo;
    String matricula;
    float kilometros_alta;
    Date fecha_alta;
    Date fecha_baja;
    int conductorid;
    int kilometros_actuales;

    public int getIdvehiculos() {
        return idvehiculos;
    }

    public void setIdvehiculos(int idvehiculos) {
        this.idvehiculos = idvehiculos;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public float getKilometros_alta() {
        return kilometros_alta;
    }

    public void setKilometros_alta(float kilometros_alta) {
        this.kilometros_alta = kilometros_alta;
    }

    public Date getFecha_alta() {
        return fecha_alta;
    }

    public void setFecha_alta(Date fecha_alta) {
        this.fecha_alta = fecha_alta;
    }

    public Date getFecha_baja() {
        return fecha_baja;
    }

    public void setFecha_baja(Date fecha_baja) {
        this.fecha_baja = fecha_baja;
    }

    public int getConductorid() {
        return conductorid;
    }

    public void setConductorid(int conductorid) {
        this.conductorid = conductorid;
    }

    public int getKilometros_actuales() {
        return kilometros_actuales;
    }

    public void setKilometros_actuales(int kilometros_actuales) {
        this.kilometros_actuales = kilometros_actuales;
    }
    
    
    
    
    
}
