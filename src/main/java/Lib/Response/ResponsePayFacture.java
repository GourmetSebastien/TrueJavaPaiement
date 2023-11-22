package Lib.Response;

import Lib.Interface.IResponse;

public class ResponsePayFacture implements IResponse {
    private boolean valide;

    public ResponsePayFacture(boolean valide) {
        this.valide = valide;
    }
    public boolean isValide() {
        return valide;
    }
}
