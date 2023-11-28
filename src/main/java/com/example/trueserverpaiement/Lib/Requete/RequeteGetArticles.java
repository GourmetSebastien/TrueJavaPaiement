package com.example.trueserverpaiement.Lib.Requete;

import com.example.trueserverpaiement.Lib.Interface.IRequete;

public class RequeteGetArticles implements IRequete {
    private int numFacture;

    public RequeteGetArticles(int num){this.numFacture = num;}

    public int getNumFacture(){return numFacture;}
}
