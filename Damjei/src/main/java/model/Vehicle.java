/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.util.Date;

/**
 * Vehicles representa els vehicles de l'empresa.
 * Cada vehicle tindrà un conductor (empleat9
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

    /**
     * La classe vehicle te les següents atributs
     * @param id_vehicle identificador del vehicle
     * @param marca marca del vehicle
     * @param model model del vehicle
     * @param matricula matricula del vehicñe
     * @param quilometres quilòmetres que pora el vehicle
     * @param data_alta data alta vehicle
     * @param data_baixa data baixa vehicle
     */
    public Vehicle(int id_vehicle, String marca, String model, String matricula, float quilometres, Date data_alta, Date data_baixa) {
        this.id_vehicle = id_vehicle;
        this.marca = marca;
        this.model = model;
        this.matricula = matricula;
        this.quilometres = quilometres;
        this.data_alta = data_alta;
        this.data_baixa = data_baixa;
    }

   /**
    * Constructor buit vehicle
    */
    public Vehicle() {
    }
    
   /**
    * Getter
    * @return id del vehicle 
    */
    public int getId_vehicle() {
        return id_vehicle;
    }

    /**
     * Setter
     * @param id_vehicle torna id del vehicle
     */
    public void setId_vehicle(int id_vehicle) {
        this.id_vehicle = id_vehicle;
    }

    /**
     * Getter
     * @return marca del vehicle 
     */  
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
