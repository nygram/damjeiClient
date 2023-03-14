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
public class Vehicle {
    int id_vehicle ;
    String marca;
    String model;
    String matricula;
    float quilometres;
    Date data_alta;
    Date data_baixa;

    public Vehicle(int id_vehicle, String marca, String model, String matricula, float quilometres, Date data_alta, Date data_baixa) {
        this.id_vehicle = id_vehicle;
        this.marca = marca;
        this.model = model;
        this.matricula = matricula;
        this.quilometres = quilometres;
        this.data_alta = data_alta;
        this.data_baixa = data_baixa;
    }

    public Vehicle() {
    }
    
    

    public int getId_vehicle() {
        return id_vehicle;
    }

    public void setId_vehicle(int id_vehicle) {
        this.id_vehicle = id_vehicle;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public float getQuilometres() {
        return quilometres;
    }

    public void setQuilometres(float quilometres) {
        this.quilometres = quilometres;
    }

    public Date getData_alta() {
        return data_alta;
    }

    public void setData_alta(Date data_alta) {
        this.data_alta = data_alta;
    }

    public Date getData_baixa() {
        return data_baixa;
    }

    public void setData_baixa(Date data_baixa) {
        this.data_baixa = data_baixa;
    }
    
    
    
    
}
