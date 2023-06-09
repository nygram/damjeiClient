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

    int idempleado;
    String nombre;
    String apellidos;
    String dni;
    String categoria;
    String contraseña;
    int empresaid;
    boolean administrador;
    String fecha_caducidad_carnet;
    String fecha_carnet;        

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public int getEmpresaid() {
        return empresaid;
    }

    public void setEmpresaid(int empresaid) {
        this.empresaid = empresaid;
    }

    public String getFecha_caducidad_carnet() {
        return fecha_caducidad_carnet;
    }

    public void setFecha_caducidad_carnet(String fecha_caducidad_carnet) {
        this.fecha_caducidad_carnet = fecha_caducidad_carnet;
    }

    public String getFecha_carnet() {
        return fecha_carnet;
    }

    public void setFecha_carnet(String fecha_carnet) {
        this.fecha_carnet = fecha_carnet;
    }
    
    

    
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
        this.nombre = nom;
        this.apellidos = cognoms;
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
        this.nombre = nom;
        this.contraseña = contrasenya;

    }

    /**
     * Getter
     * @return id de l'empresa
     */
 

    /**
     * Getter
     * @return nom empleat
     */
    public String getNom() {
        return nombre;
    }

    /**
     * Setter
     * @param nom
     */
    public void setNom(String nom) {
        this.nombre = nom;
    }

    /**
     * Getter
     * @return cognoms empleat
     */
    public String getCognoms() {
        return apellidos;
    }

    /**
     * Setter
     * @param cognoms
     */
    public void setCognoms(String cognoms) {
        this.apellidos = cognoms;
    }

    /**
     * Getter
     * @return dni emplat
     */
    public String getDni() {
        return dni;
    }

    /**
     * Setter
     * @param dni
     */
    public void setDni(String dni) {
        this.dni = dni;
    }

    /**
     * Getter
     * @return categoria
     */
    public String getCategoria() {
        return categoria;
    }

    /**
     * Setter
     * @param categoria
     */
    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    /**
     * Getter
     * @return contrasenya
     */
    public String getContrasenya() {
        return contraseña;
    }

    /**
     * Setter
     * @param contrasenya
     */
    public void setContrasenya(String contrasenya) {
        this.contraseña = contrasenya;
    }

    public int getIdempleado() {
        return idempleado;
    }

    public void setIdempleado(int idempleado) {
        this.idempleado = idempleado;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public boolean getAdministrador() {
        return administrador;
    }

    public void setAdministrador(boolean administrador) {
        this.administrador = administrador;
    }
    
    @Override
    public String toString() {
        return this.dni;
    }
    
    

}
