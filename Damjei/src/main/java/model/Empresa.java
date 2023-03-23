/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author JavierFernándezDíaz
 */
public class Empresa {
    
    int id_empresa;
    String nom;
    String ciutat;
    String direccio;
    String cif;

    /**
     *
     * @param id_empresa
     * @param nom
     * @param ciutat
     * @param direccio
     * @param cif
     */
    public Empresa(int id_empresa, String nom, String ciutat, String direccio, String cif) {
        this.id_empresa = id_empresa;
        this.nom = nom;
        this.ciutat = ciutat;
        this.direccio = direccio;
        this.cif = cif;
    }

    /**
     *
     */
    public Empresa() {
    }

    /**
     *
     * @return
     */
    public int getId_empresa() {
        return id_empresa;
    }

    /**
     *
     * @param id_empresa
     */
    public void setId_empresa(int id_empresa) {
        this.id_empresa = id_empresa;
    }

    /**
     *
     * @return
     */
    public String getNom() {
        return nom;
    }

    /**
     *
     * @param nom
     */
    public void setNom(String nom) {
        this.nom = nom;
    }

    /**
     *
     * @return
     */
    public String getCiutat() {
        return ciutat;
    }

    /**
     *
     * @param ciutat
     */
    public void setCiutat(String ciutat) {
        this.ciutat = ciutat;
    }

    /**
     *
     * @return
     */
    public String getDireccio() {
        return direccio;
    }

    /**
     *
     * @param direccio
     */
    public void setDireccio(String direccio) {
        this.direccio = direccio;
    }

    /**
     *
     * @return
     */
    public String getCif() {
        return cif;
    }

    /**
     *
     * @param cif
     */
    public void setCif(String cif) {
        this.cif = cif;
    }
    
    
    
    
}
