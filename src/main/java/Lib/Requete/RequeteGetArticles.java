package Lib.Requete;

import Lib.Interface.IRequete;

public class RequeteGetArticles implements IRequete {
    private int numFacture;

    public RequeteGetArticles(int num){this.numFacture = num;}

    public int getNumFacture(){return numFacture;}
}
