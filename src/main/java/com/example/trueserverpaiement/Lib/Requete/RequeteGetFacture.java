package com.example.trueserverpaiement.Lib.Requete;

import com.example.trueserverpaiement.Lib.Interface.IRequete;

public class RequeteGetFacture implements IRequete {
    private int numClient;

    public RequeteGetFacture(int numClient){this.numClient = numClient;}

    public int getNumClient() {return numClient;}
}
