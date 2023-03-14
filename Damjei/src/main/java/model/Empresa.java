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

    public Empresa(int id_empresa, String nom, String ciutat, String direccio, String cif) {
        this.id_empresa = id_empresa;
        this.nom = nom;
        this.ciutat = ciutat;
        this.direccio = direccio;
        this.cif = cif;
    }

    
    
    public Empresa() {
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

    public String getCiutat() {
        return ciutat;
    }

    public void setCiutat(String ciutat) {
        this.ciutat = ciutat;
    }

    public String getDireccio() {
        return direccio;
    }

    public void setDireccio(String direccio) {
        this.direccio = direccio;
    }

    public String getCif() {
        return cif;
    }

    public void setCif(String cif) {
        this.cif = cif;
    }
    
    
    
    
}
