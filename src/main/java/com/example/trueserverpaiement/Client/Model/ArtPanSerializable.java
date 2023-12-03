package com.example.trueserverpaiement.Client.Model;

import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import java.io.Serializable;

public class ArtPanSerializable implements Serializable {
    private int id;
    private String intitule;
    private float prixUnitaire;
    private int quantite;

    public ArtPanSerializable(){
        this.id = -1;
        this.intitule = "";
        this.prixUnitaire = -1;
        this.quantite = -1;
    }

    public ArtPanSerializable(int id, String intitule, float prixUnitaire, int quantite){
        this.id = id;
        this.intitule = intitule;
        this.prixUnitaire = prixUnitaire;
        this.quantite = quantite;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    public int getQuantite() {
        return quantite;
    }

    public float getPrixUnitaire() {
        return prixUnitaire;
    }

    public void setPrixUnitaire(float prixUnitaire) {
        this.prixUnitaire = prixUnitaire;
    }

    public void setIntitule(String intitule) {
        this.intitule = intitule;
    }

    public String getIntitule() {
        return intitule;
    }

    @Override
    public String toString() {
        return "ArtPanSerializable{" +
                "id=" + id +
                ", intitule='" + intitule + '\'' +
                ", prixUnitaire=" + prixUnitaire +
                ", quantite=" + quantite +
                '}';
    }
}
