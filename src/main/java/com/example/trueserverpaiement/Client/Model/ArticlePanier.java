package com.example.trueserverpaiement.Client.Model;

import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class ArticlePanier {
    private transient SimpleIntegerProperty Id;
    private transient SimpleStringProperty intitule;
    private transient SimpleFloatProperty prixUnitaire;
    private transient SimpleIntegerProperty quantite;

    public SimpleIntegerProperty idProperty(){return Id;}
    public SimpleStringProperty intituleProperty(){return intitule;}
    public SimpleFloatProperty prixUnitaireProperty(){return prixUnitaire;}
    public SimpleIntegerProperty quantiteProperty(){return quantite;}

    public ArticlePanier(){
        this.Id = new SimpleIntegerProperty();
        this.intitule = new SimpleStringProperty();
        this.quantite = new SimpleIntegerProperty();
        this.prixUnitaire = new SimpleFloatProperty();
    }
    public ArticlePanier(Integer id,String inti, Float prixUnit, Integer quant){
        this.Id = new SimpleIntegerProperty(id);
        this.intitule = new SimpleStringProperty(inti);
        this.prixUnitaire = new SimpleFloatProperty(prixUnit);
        this.quantite = new SimpleIntegerProperty(quant);
    }

    public Integer getId(){return Id.get();}
    public void setId(Integer id){setId(id);}
    public String getIntitule(){return intitule.get();}

    public void setIntitule(String intitule) {setIntitule(intitule);}

    public Float getPrixUnitaire() {return prixUnitaire.get();}

    public void setPrixUnitaire(Float prixUnitaire) {setPrixUnitaire(prixUnitaire);}

    public Integer getQuantite() {return quantite.get();}

    public void setQuantite(Integer quantite) {setQuantite(quantite);}
}
