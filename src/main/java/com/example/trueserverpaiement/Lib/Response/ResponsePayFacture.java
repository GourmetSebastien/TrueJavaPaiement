package com.example.trueserverpaiement.Lib.Response;

import com.example.trueserverpaiement.Lib.Interface.IResponse;

public class ResponsePayFacture implements IResponse {
    private boolean valide;

    public ResponsePayFacture(boolean valide) {
        this.valide = valide;
    }
    public boolean isValide() {
        return valide;
    }
}
