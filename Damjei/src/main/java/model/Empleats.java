/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 * Empleats representa els empleats (administradors
 * o no) que accedeixen a l'aplicació i que poden ser assignats a un vehicle.
 * Aquests empleats registraran els manteniments i els repostatges.
 * @author JavierFernándezDíaz 
 */
public class Empleats {

    String nom;
    String cognoms;
    String dni;
    String categoria;
    String contrasenya;
    int id_empresa;
    Boolean administrador;

    
    public Empleats() {
    }

    /**
     * Crea un empleat rebent com a paràmetres nom, cognoms, dni i categoria
     *
     * @param nom defineix el nom de l'empleat
     * @param cognoms defineix els cognomes de l'empleat
     * @param dni defineix el dni de l'empleat
     * @param categoria defineix el categoria de l'empleat
     */
    public Empleats(String nom, String cognoms, String dni, String categoria) {
        //this.id_empresa = id_empresa;
        this.nom = nom;
        this.cognoms = cognoms;
        this.dni = dni;
        this.categoria = categoria;
    }

    /**
     * Crear un emplat rebent nom i contrasenya per poder validar
     *
     * @param nom defineix el nom de l'empleat
     * @param contrasenya defineix la contrasenya de l'empleat
     */
    public Empleats(String nom, String contrasenya) {
        this.nom = nom;
        this.contrasenya = contrasenya;

    }

    /**
     * Getter
     *
     * @return id de l'empresa
     */
    public int getId_empresa() {
        return id_empresa;
    }

    /**
     * Setter
     *
     * @param id_empresa
     */
    public void setId_empresa(int id_empresa) {
        this.id_empresa = id_empresa;
    }

    /**
     * Getter
     *
     * @return nom empleat
     */
    public String getNom() {
        return nom;
    }

    /**
     * Setter
     *
     * @param nom
     */
    public void setNom(String nom) {
        this.nom = nom;
    }

    /**
     * Getter
     *
     * @return cognoms empleat
     */
    public String getCognoms() {
        return cognoms;
    }

    /**
     * Setter
     *
     * @param cognoms
     */
    public void setCognoms(String cognoms) {
        this.cognoms = cognoms;
    }

    /**
     * Getter
     *
     * @return dni emplat
     */
    public String getDni() {
        return dni;
    }

    /**
     * Setter
     *
     * @param dni
     */
    public void setDni(String dni) {
        this.dni = dni;
    }

    /**
     * Getter
     *
     * @return categoria
     */
    public String getCategoria() {
        return categoria;
    }

    /**
     * Setter
     *
     * @param categoria
     */
    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    /**
     * Getter
     *
     * @return contrasenya
     */
    public String getContrasenya() {
        return contrasenya;
    }

    /**
     * Setter
     *
     * @param contrasenya
     */
    public void setContrasenya(String contrasenya) {
        this.contrasenya = contrasenya;
    }

}
