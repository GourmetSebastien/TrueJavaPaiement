package com.example.trueserverpaiement.Lib.Response;

import com.example.trueserverpaiement.Lib.Interface.IResponse;

public class ResponseLogout implements IResponse {
    private boolean ok;

    public ResponseLogout(boolean ok) {
        this.ok = ok;
    }

    public void setOk(boolean ok) {
        this.ok = ok;
    }

    public boolean isOk() {
        return ok;
    }
}
