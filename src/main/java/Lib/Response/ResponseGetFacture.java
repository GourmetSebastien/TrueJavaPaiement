package Lib.Response;

import Client.Model.Facture;
import Lib.Interface.IResponse;

import java.util.List;

public class ResponseGetFacture implements IResponse {
    private final List<Facture> factures;
    private boolean isFound= false;

    public ResponseGetFacture(){factures = null;}

    public ResponseGetFacture(List<Facture> articles, boolean isFound){
        this.factures = articles;
        this.isFound = isFound;
    }

    public List<Facture> getListArticles() {
        return factures;
    }

    public boolean isFound() {
        return isFound;
    }
}
