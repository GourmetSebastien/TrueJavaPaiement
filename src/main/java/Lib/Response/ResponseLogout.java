package Lib.Response;

import Lib.Interface.IResponse;

public class ResponseLogout implements IResponse {
    private final boolean ok;

    public ResponseLogout(boolean ok) {
        this.ok = ok;
    }

    public boolean isOk() {
        return ok;
    }
}
