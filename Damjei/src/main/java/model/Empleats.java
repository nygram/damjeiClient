/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author JavierFernándezDíaz
 */
public class Empleats {
    
    int id_empresa;
    String nom;
    String cognoms;
    String dni;
    String categoria;
    String contrasenya;
    Boolean administrador;

    public Empleats() {
    }

    public Empleats(int id_empresa, String nom, String cognoms, String dni, String categoria) {
        this.id_empresa = id_empresa;
        this.nom = nom;
        this.cognoms = cognoms;
        this.dni = dni;
        this.categoria = categoria;
    }

    public int getId_empresa() {
        return id_empresa;
    }

    public void setId_empresa(int id_empresa) {
        this.id_empresa = id_empresa;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getCognoms() {
        return cognoms;
    }

    public void setCognoms(String cognoms) {
        this.cognoms = cognoms;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }
    
    public String getContrasenya() {
        return contrasenya;
    }

    public void setContrasenya(String contrasenya) {
        this.contrasenya = contrasenya;
    }

    
    
}
