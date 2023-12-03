package com.example.trueserverpaiement.Client.Model;

import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import java.io.Serializable;

public class Facture {
    private transient SimpleIntegerProperty Id;
    private transient SimpleStringProperty date;
    private transient SimpleFloatProperty PrixTotal;
    private transient SimpleIntegerProperty Payer;

    public SimpleIntegerProperty IdProperty(){return Id;}
    public SimpleStringProperty dateProperty(){return date;}
    public SimpleFloatProperty PrixTotalProperty(){return PrixTotal;}
    public SimpleIntegerProperty PayerProperty(){return Payer;}

    public Facture(){
        this.Id =new SimpleIntegerProperty();
        this.date =new SimpleStringProperty();
        this.PrixTotal=new SimpleFloatProperty();
        this.Payer=new SimpleIntegerProperty();
    }

    public Facture(int Num, String date, float Prix, int p){
        this.Id =new SimpleIntegerProperty(Num);
        this.date= new SimpleStringProperty(date);
        this.PrixTotal=new SimpleFloatProperty(Prix);
        this.Payer=new SimpleIntegerProperty(p);
    }

    public Integer getId(){return this.Id.get();}
    public void setId(Integer Id){this.Id.set(Id);}
    public String getDate(){return this.date.get();}
    public void setDate(String date){this.date.set(date);}
    public Float getPrixTotal(){return this.PrixTotal.get();}
    public void setPrixTotal(Float prixTotal){this.PrixTotal.set(prixTotal);}
    public Integer getPayer(){return this.Payer.get();}
    public void setPayer(Integer payer){this.Payer.set(payer);}
}
