package Lib.Response;

import Lib.Interface.IResponse;

public class ResponseLogin implements IResponse {
    private final boolean valide;
    private final String message;

    public ResponseLogin(boolean valide, String message){
        this.valide = valide;
        this.message = message;
    }

    public boolean isValide() {
        return valide;
    }

    public String getMessage() {
        return message;
    }
}
