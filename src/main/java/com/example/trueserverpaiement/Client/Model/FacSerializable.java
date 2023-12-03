package com.example.trueserverpaiement.Client.Model;

import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import java.io.Serializable;

public class FacSerializable implements Serializable {
    private int id;
    private String date;
    private float prixTotal;
    private int payer;

    public FacSerializable(){
        id = -1;
        date = "";
        prixTotal = -1;
        payer = -1;
    }

    public FacSerializable(int id, String date, float prixTotal, int payer){
        this.id = id;
        this.date = date;
        this.prixTotal = prixTotal;
        this.payer = payer;
    }

    public void setPrixTotal(float prixTotal) {
        this.prixTotal = prixTotal;
    }

    public float getPrixTotal() {
        return prixTotal;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setPayer(int payer) {
        this.payer = payer;
    }

    public int getPayer() {
        return payer;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDate() {
        return date;
    }
}
