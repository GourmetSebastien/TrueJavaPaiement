package com.example.trueserverpaiement.Lib.Requete;

import com.example.trueserverpaiement.Lib.Interface.IRequete;

public class RequeteLogout implements IRequete {
    private String Login;

    public RequeteLogout(String login){this.Login = login;}

    public void setLogin(String login) {
        Login = login;
    }

    public String getLogin(){return Login;}
}
