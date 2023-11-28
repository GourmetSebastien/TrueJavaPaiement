package com.example.trueserverpaiement.Client.Model;

import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

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

    public Facture(Integer Num, String date, Float Prix, Integer p){
        this.Id =new SimpleIntegerProperty(Num);
        this.date= new SimpleStringProperty(date);
        this.PrixTotal=new SimpleFloatProperty(Prix);
        this.Payer=new SimpleIntegerProperty(p);
    }

    public Integer getId(){return Id.get();}
    public void setId(Integer Id){setId(Id);}

    public String getDate(){return date.get();}
    public void setDate(String date){setDate(date);}
    public Float getPrixTotal(){return PrixTotal.get();}
    public void setPrixTotal(Float prixTotal){setPrixTotal(prixTotal);}
    public Integer getPayer(){return Payer.get();}
    public void setPayer(Integer payer){setPayer(payer);}
}
