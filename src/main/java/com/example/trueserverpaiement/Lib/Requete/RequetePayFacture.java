package com.example.trueserverpaiement.Lib.Requete;

import com.example.trueserverpaiement.Lib.Interface.IRequete;

public class RequetePayFacture implements IRequete {
    private int IdFacture;
    private String NumCarte;
    private String NomClient;

    public RequetePayFacture(int idFacture,String num, String nom){
        this.IdFacture = idFacture;
        this.NomClient = nom;
        this.NumCarte = num;
    }

    public int getIdFacture() {
        return IdFacture;
    }

    public String getNumCarte() {
        return NumCarte;
    }

    public String getTypeCarte() {
        return NomClient;
    }

    public void setIdFacture(int idFacture) {
        this.IdFacture = idFacture;
    }

    public void setNumCarte(String numCarte) {
        this.NumCarte = numCarte;
    }

    public void setTypeCarte(String nomClient) {
        this.NomClient = nomClient;
    }
}
