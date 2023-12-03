package com.example.trueserverpaiement.Lib.Response;

import com.example.trueserverpaiement.Client.Model.FacSerializable;
import com.example.trueserverpaiement.Client.Model.Facture;
import com.example.trueserverpaiement.Lib.Interface.IResponse;

import java.util.ArrayList;
import java.util.List;

public class ResponseGetFacture implements IResponse {
    private List<FacSerializable> factures;
    private boolean isFound= false;

    public ResponseGetFacture(){factures = null;}

    public ResponseGetFacture(List<FacSerializable> articles, boolean isFound){
        this.factures = new ArrayList<>();
        this.factures.addAll(articles);
        this.isFound = isFound;
    }

    public List<FacSerializable> getListFacture() {
        return factures;
    }

    public boolean isFound() {
        return isFound;
    }
}
