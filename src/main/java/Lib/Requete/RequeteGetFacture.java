package Lib.Requete;

import Lib.Interface.IRequete;

public class RequeteGetFacture implements IRequete {
    private int numClient;

    public RequeteGetFacture(int numClient){this.numClient = numClient;}

    public int getNumClient() {return numClient;}
}
